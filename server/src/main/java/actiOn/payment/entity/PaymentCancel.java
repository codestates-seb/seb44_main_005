package actiOn.payment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentCancel {
    @Id
    private Long cancelId;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;
}
