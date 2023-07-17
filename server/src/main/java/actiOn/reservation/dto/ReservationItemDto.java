package actiOn.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
@Builder
@AllArgsConstructor
public class ReservationItemDto {
    private Long itemId;

    @Positive
    private int ticketCount;
}