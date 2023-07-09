package actiOn.reservation.dto;

import actiOn.reservation.entity.ReservationItem;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
public class ReservationReqDto {

        @NotBlank(message = "예약자 성함은 필수 값입니다.")
        private String reservationName;

        @NotBlank(message = "예약자 핸드폰은 필수 값입니다.")
        private String reservationPhone;

        private String reservationEmail;

        private Date reservationDate;

        private List<ReservationItemReqDto> reservationItemReqDtos;

}
