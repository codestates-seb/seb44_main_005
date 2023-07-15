package actiOn.reservation.dto;

import actiOn.helper.validator.NotSpace;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Builder
public class ReservationPostDto {
    @NotSpace(message = "예약자 성함은 필수 값입니다.")
    private String reservationName;

    @NotSpace(message = "예약자 핸드폰은 필수 값입니다.")
    @Pattern(regexp = "^(010|011)-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "휴대폰 번호는 010(혹은 011)만 가능하며, 하이픈 '-'을 포함하여 작성해주세요.")
    private String reservationPhone;

    @NotSpace
    private String reservationEmail;

    @NotSpace
    private String reservationDate;

    @Positive
    private int totalPrice;

    private List<ReservationItemDto> reservationItems;

    @Getter
    @Builder
    public static class ReservationItemDto {
        private Long itemId;

        @Positive
        private int ticketCount;
    }
}
