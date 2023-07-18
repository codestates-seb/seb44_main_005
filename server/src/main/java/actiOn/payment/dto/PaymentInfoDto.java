package actiOn.payment.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoDto {
    private String orderId;
    private String paymentKey;
    private String orderName;
    private String method;
    private int totalAmount;
    private String requestedAt;
    private String status;

}
