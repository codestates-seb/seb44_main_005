package actiOn.payment.mapper;

import actiOn.payment.dto.PaymentDto;
import actiOn.payment.dto.PaymentResponseDto;
import actiOn.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PaymentMapper {
    Payment paymentDtoToPayment(PaymentDto requestBody);

    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);
}
