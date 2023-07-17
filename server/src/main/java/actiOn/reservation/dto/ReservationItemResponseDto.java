package actiOn.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReservationItemResponseDto {
    private String itemName;
    private int ticketCount;
}