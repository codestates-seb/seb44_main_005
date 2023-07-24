package actiOn.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class ReservationResponseDto {
    private String storeName;
    private LocalDate reservationDate;
    private int totalPrice;
    private String reservationName;
    private String reservationPhone;
    private String reservationEmail;
    private List<ReservationItemResponseDto> reservationItems;
}
