package actiOn.payment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancelId;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;
}
