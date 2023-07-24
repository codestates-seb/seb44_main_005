package actiOn.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmDto {
    private Long Amount;
    private String orderId;
    private String paymentKey;
}
