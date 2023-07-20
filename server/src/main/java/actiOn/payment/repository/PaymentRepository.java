package actiOn.payment.repository;

import actiOn.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByPaymentKeyAndCustomer_Email(String paymentKey, String email);

    Payment findByPaymentKey(String payment);
}
