package actiOn.payment.dto;


import actiOn.payment.entity.Payment;
import actiOn.payment.entity.PaymentCancel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentInfoDto {
    private String orderId;
    private String paymentKey;
    private Payment.PayType type;
//    private PaymentCancel cancels;
    private String orderName;
    private Payment.PayMethod method;
    private Long totalAmount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime requestedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime approvedAt;
    private Payment.Status status;

}
