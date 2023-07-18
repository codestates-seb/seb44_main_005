package actiOn.payment.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.map.response.KakaoMapResponse;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.payment.dto.PaymentInfoDto;
import actiOn.payment.entity.Payment;
import actiOn.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
//@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    public PaymentService(PaymentRepository paymentRepository, MemberService memberService){
        this.paymentRepository = paymentRepository;
        this.memberService = memberService;
    }

    @Value("${TOSS_CLIENT_KEY}")
    @Getter
    private String clientKey;
    @Value("${TOSS_SECRET_KEY}")
    @Getter
    private String secretKey;
    private String tossPaymentURL = "https://api.tosspayments.com/v1/payments/orders/";

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

    public void sendPaymentRequest() {

    }

    private String generateAuthToBase64(){
        //Todo base64를 이용해서, secretKey를 인코딩 해야함
        byte[] secretKeyBytes = (secretKey+":").getBytes(StandardCharsets.UTF_8);
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKeyBytes);
        return encodedSecretKey;
    }
    public PaymentInfoDto getPaymentInfoByOrderId(String orderId){  //private 으로 바꿔야 함
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + generateAuthToBase64());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = tossPaymentURL + orderId;

        ResponseEntity<PaymentInfoDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, PaymentInfoDto.class);
        PaymentInfoDto response = responseEntity.getBody();
        System.out.println(response.getTotalAmount());
        return response;
    }
}
