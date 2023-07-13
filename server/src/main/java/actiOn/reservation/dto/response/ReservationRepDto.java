package actiOn.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class ReservationRepDto {

    private String storeName;
    private LocalDate reservationDate;
    private int totalPrice;
    private String reservationName;
    private String reservationPhone;
    private String reservationEmail;
    private List<ReservationItemRepDto> reservationItemRepDtos;
}
