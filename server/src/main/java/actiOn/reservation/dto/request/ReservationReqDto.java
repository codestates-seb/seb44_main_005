package actiOn.reservation.dto.request;

import actiOn.reservation.dto.request.ReservationItemReqDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class ReservationReqDto {

        @NotBlank(message = "예약자 성함은 필수 값입니다.")
        private String reservationName;

        @NotBlank(message = "예약자 핸드폰은 필수 값입니다.")
        @Pattern(regexp = "^(010|011)-(?:\\d{3}|\\d{4})-\\d{4}$",
                message = "휴대폰 번호는 010(혹은 011)만 가능하며, 하이픈 '-'을 포함하여 작성해주세요.")
        private String reservationPhone;

        private String reservationEmail;

        private String reservationDate;

        @Positive
        private int totalPrice;

        private List<ReservationItemReqDto> reservationItems;

}
