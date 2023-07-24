package actiOn.payment.mapper;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.payment.dto.PaymentDto;
import actiOn.payment.dto.PaymentInfoDto;
import actiOn.payment.dto.PaymentResponseDto;
import actiOn.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PaymentMapper {
    Payment paymentDtoToPayment(PaymentDto requestBody);

    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);

    default Payment paymentInfoDtoToPayment(PaymentInfoDto paymentInfoDto){
        if (paymentInfoDto==null) {
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_PAYMENT);
        }
        Payment payment = new Payment();
        payment.setPaymentKey(paymentInfoDto.getPaymentKey());
        payment.setOrderName(paymentInfoDto.getOrderName());
        payment.setPayType(paymentInfoDto.getType());
        payment.setPayMethod(paymentInfoDto.getMethod());
        payment.setOrderId(paymentInfoDto.getOrderId());
        payment.setTotalAmount(paymentInfoDto.getTotalAmount());
        payment.setStatus(paymentInfoDto.getStatus());
        payment.setApprovedAt(paymentInfoDto.getApprovedAt());
        payment.setRequestedAt(paymentInfoDto.getRequestedAt());
        return payment;
    }
}


