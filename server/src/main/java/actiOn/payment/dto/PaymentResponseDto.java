package actiOn.payment.dto;

import actiOn.member.entity.Member;
import actiOn.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private String payType;
    private Long amount;
    private String orderName;
    private String paymentKey;
    private String orderId;
    private String customerEmail;
    private String customerName;
//    private String successUrl;
//    private String failUrl;

    private Payment.Status status;
    private String failReason;
//    private boolean cancelYN;
    private String cancelReason;
    private String createdAt;
}
