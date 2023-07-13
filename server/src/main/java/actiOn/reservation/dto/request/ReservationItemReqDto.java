package actiOn.reservation.dto.request;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class ReservationItemReqDto {

    private Long itemId;

    @Positive
    private int ticketCount;
}
