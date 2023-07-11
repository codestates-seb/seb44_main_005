package actiOn.payment.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.payment.entity.Payment;
import actiOn.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

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

    public void sendPaymentRequest() {

    }

    public void handlePaymentResponse(Payment payment) {

    }
}
