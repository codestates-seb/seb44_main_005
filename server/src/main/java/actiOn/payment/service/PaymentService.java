package actiOn.payment.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.payment.dto.PaymentCancelDto;
import actiOn.payment.dto.PaymentConfirmDto;
import actiOn.payment.dto.PaymentInfoDto;
import actiOn.payment.entity.Payment;
import actiOn.payment.entity.PaymentCancel;
import actiOn.payment.repository.PaymentCancelRepository;
import actiOn.payment.repository.PaymentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;
    private final PaymentCancelRepository paymentCancelRepository;

    @Value("${payment.toss.test-client-key}")
    @Getter
    private String clientKey;

    @Value("${payment.toss.test-secret-key}")
    @Getter
    private String secretKey;

    @Value("${payment.toss.url}")
    private String tossPaymentURL;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Payment requestPayment(String email, Payment payment) {
        Member customer = memberService.findMemberByEmail(email);

        // 총 결제 금액이 0 이하인 경우
        if (payment.getTotalAmount() <= 0) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PAYMENT_AMOUNT);
        }

        payment.setCustomer(customer);
        return paymentRepository.save(payment);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void handlePaymentSuccess(Payment payment, Long totalAmount, String email) {
        Member customer = memberService.findMemberByEmail(email);

        verifyTotalAmount(payment, totalAmount);

        payment.setCustomer(customer);
    }

    // 존재하는 결제인지 확인
    public Payment findExistsPayment(String orderId) {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);

        if (payment.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.PAYMENT_NOT_FOUND);
        }
        return payment.get();
    }

    // 총 결제 금액 일치하는지 확인
    private void verifyTotalAmount(Payment payment, long totalAmount) {
        if (payment.getTotalAmount() != totalAmount) {
            throw new BusinessLogicException(ExceptionCode.PAYMENT_AMOUNT_MISMATCH);
        }
    }

    /**    =========================================================   */
    /**
     * 여기부터는 토스페이먼츠로 거래 내역을 불러오는 기능을 담당합니다.
     **/
    private String generateAuthToBase64() {
        //Todo base64를 이용해서, secretKey를 인코딩 해야함
        byte[] secretKeyBytes = (secretKey + ":").getBytes(StandardCharsets.UTF_8);
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKeyBytes);
        return encodedSecretKey;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + generateAuthToBase64());
        return headers;
    }

    public PaymentInfoDto getPaymentInfoByOrderId(String orderId) {  //private 으로 바꿔야 함
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = createHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            String url = tossPaymentURL + "orders/" + orderId;
            ResponseEntity<PaymentInfoDto> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, requestEntity, PaymentInfoDto.class);
            PaymentInfoDto response = responseEntity.getBody();

            if (response.getStatus().equals(Payment.Status.IN_PROGRESS)) {
                response = confirmPayment(response.getOrderId(),
                        response.getPaymentKey(),
                        response.getTotalAmount()).getBody();
            }
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_PAYMENT);//
        }
    }

    private ResponseEntity<PaymentInfoDto> confirmPayment(String orderId, String paymentKey, long amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders();
        PaymentConfirmDto paymentConfirmDto = new PaymentConfirmDto();
        paymentConfirmDto.setPaymentKey(paymentKey);
        paymentConfirmDto.setAmount(amount);
        paymentConfirmDto.setOrderId(orderId);

        HttpEntity<PaymentConfirmDto> requestEntity = new HttpEntity<>(paymentConfirmDto, headers); // 헤더와 바디를 합쳐서 객체 생성
        String url = tossPaymentURL + "/confirm"; // 요청보낼 곳
        return restTemplate.postForEntity(url, requestEntity, PaymentInfoDto.class); // 요청
    }

/**    =========================================================   */
    /**
     * 여기부터는 결제정보를 디비에 저장하는 역할을 합니다. 결제에 실패하거나 잘못된 요청이더라도 일단 결제 정보를 저장해야만 합니다.
     */
    public Payment saveToPaymentRepository(Payment payment) {
        Member member = memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail());
        payment.setCustomer(member);
        return paymentRepository.save(payment);
    }

    /**    =========================================================   */
    /**
     * 여기부터는 환불기능을 담당합니다.
     */
    public void refund(String paymentKey, Long cancelAmount) {
        Payment payment = paymentRepository.findByPaymentKey(paymentKey);
        if (payment == null) {
            throw new BusinessLogicException(ExceptionCode.PAYMENT_NOT_FOUND);
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = createHeaders();
            PaymentCancelDto requestData = new PaymentCancelDto(); // body 객체 생성
            // requestData에 필요한 데이터 설정
            requestData.setCancelAmount(cancelAmount); // body에 data set
            HttpEntity<PaymentCancelDto> requestEntity = new HttpEntity<>(requestData, headers); // 헤더와 바디를 합쳐서 객체 생성
            String url = tossPaymentURL + paymentKey + "/cancel"; // 요청보낼 곳
            restTemplate.postForEntity(url, requestEntity, PaymentInfoDto.class); // 요청
            refundUpdateRepository(payment);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }
    }

    public void refundUpdateRepository(Payment payment) throws BusinessLogicException {
        PaymentCancel paymentCancel = new PaymentCancel();
        paymentCancel.setPayment(payment);
        paymentCancelRepository.save(paymentCancel);
        payment.setCancel(paymentCancel);
        payment.setStatus(Payment.Status.CANCELED);
        paymentRepository.save(payment);
    }
}
