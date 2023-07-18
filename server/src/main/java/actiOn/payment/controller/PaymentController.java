package actiOn.payment.controller;

import actiOn.auth.utils.AuthUtil;
import actiOn.config.payments.TossPaymentsConfiguration;
import actiOn.member.service.MemberService;
import actiOn.payment.dto.PaymentDto;
import actiOn.payment.dto.PaymentResponseDto;
import actiOn.payment.entity.Payment;
import actiOn.payment.mapper.PaymentMapper;
import actiOn.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final TossPaymentsConfiguration configuration;
    private final PaymentMapper mapper;

    // 이 요청이 성공하면 토스페이먼츠에 결제 요청을 보냄
    @PostMapping("/toss")
    public ResponseEntity requestPayment(@RequestBody @Valid PaymentDto requestBody) {
        String email = AuthUtil.getCurrentMemberEmail();
        Payment payment = mapper.paymentDtoToPayment(requestBody);

        payment = paymentService.requestPayment(email, payment);
        PaymentResponseDto response = mapper.paymentToPaymentResponseDto(payment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 결제 성공시 리디렉션되는 엔드포인트
    @GetMapping("/toss/success")
    public ResponseEntity tossPaymentSuccess(@RequestParam @Valid String paymentKey,
                                             @RequestParam @Valid String orderId,
                                             @RequestParam @Valid Long totalAmount) {
        String email = AuthUtil.getCurrentMemberEmail();
        Payment payment = paymentService.findExistsPayment(orderId);

        paymentService.handlePaymentSuccess(payment, totalAmount, email);

        PaymentResponseDto response = mapper.paymentToPaymentResponseDto(payment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payments") //임시로 만든 엔드포인트
    public ResponseEntity successPayment(@RequestParam("order-id") String orderId){
        paymentService.getPaymentInfoByOrderId(orderId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
