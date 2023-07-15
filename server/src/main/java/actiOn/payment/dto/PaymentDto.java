package actiOn.payment.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class PaymentDto {
    @NotBlank
    private String paymentKey;

    @NotBlank
    private String payType;

    @NotBlank
    private String payMethod;

    @NotBlank
    @Range(min = 0, max = 100_000_000)
    private Long totalAmount;

    @NotBlank
    private String orderName;

}
