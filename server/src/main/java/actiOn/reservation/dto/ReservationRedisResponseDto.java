package actiOn.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRedisResponseDto {
    private String reservationKey;

    public ReservationRedisResponseDto(String reservationKey){
        this.reservationKey = reservationKey;
    }
}
