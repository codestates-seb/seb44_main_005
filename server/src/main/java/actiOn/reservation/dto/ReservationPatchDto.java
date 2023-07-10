package actiOn.reservation.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class ReservationPatchDto {
    @NotBlank(message = "예약자 이름은 필수 값입니다.")
    private String reservationName;

    @NotBlank(message = "예약자 핸드폰 번호는 필수 값입니다.")
    @Pattern(regexp = "^(010|011)-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "휴대폰 번호는 010(혹은 011)만 가능하며, 하이픈 '-'을 포함하여 작성해주세요.")
    private String reservationPhone;

    private String reservationEmail;
}
