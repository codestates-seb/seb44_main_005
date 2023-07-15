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

import java.util.Optional;

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

}
