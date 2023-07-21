package actiOn.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCancelDto {
    private Long cancelAmount;
    private String cancelReason = "고객변심";
}
