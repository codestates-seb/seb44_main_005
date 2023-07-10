package actiOn.reservation.mapper;

import actiOn.item.entity.Item;
import actiOn.reservation.dto.request.ReservationItemReqDto;
import actiOn.reservation.dto.request.ReservationPatchDto;
import actiOn.reservation.dto.response.ReservationItemRepDto;
import actiOn.reservation.dto.response.ReservationRepDto;
import actiOn.reservation.dto.request.ReservationReqDto;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationMapper {

    public Reservation reservationReqDtoToReservation(ReservationReqDto reservationReqDto) {
        Reservation reservation = new Reservation();
        reservation.setReservationName(reservationReqDto.getReservationName());
        reservation.setReservationPhone(reservationReqDto.getReservationPhone());
        reservation.setReservationEmail(reservationReqDto.getReservationEmail());
        reservation.setReservationDate(LocalDate.parse(reservationReqDto.getReservationDate()));
        reservation.setTotalPrice(reservationReqDto.getTotalPrice());

        List<ReservationItemReqDto> itemReqDtos = reservationReqDto.getReservationItemReqDtos();
        List<ReservationItem> reservationItems = new ArrayList<>();
        for (ReservationItemReqDto itemReqDto : itemReqDtos) {
            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setTicketCount(itemReqDto.getTicketCount());

            //Item 객체 매핑
            Item item = new Item();
            item.setItemId(itemReqDto.getItemId());
            reservationItem.setItem(item);

            reservationItems.add(reservationItem);
        }
        reservation.setReservationItems(reservationItems);
        return reservation;
    }

    public Reservation reservationPatchDtoToReservation(ReservationPatchDto reservationPatchDto){
        return new Reservation(
                reservationPatchDto.getReservationName(),
                reservationPatchDto.getReservationPhone(),
                reservationPatchDto.getReservationEmail()
        );
    }

    public ReservationRepDto reservationToReservationRepDto(Reservation reservation){
        List<ReservationItemRepDto> reservationItemRepDtos = new ArrayList<>();
        List<ReservationItem> reservationItems = reservation.getReservationItems();
        for(ReservationItem reservationItem : reservationItems){
            ReservationItemRepDto reservationItemRepDto = new ReservationItemRepDto();
            reservationItemRepDto.setItemName(reservationItem.getItem().getItemName());
            reservationItemRepDto.setTicketCount(reservationItem.getTicketCount());

            reservationItemRepDtos.add(reservationItemRepDto);
        }

        ReservationRepDto reservationRepDto = new ReservationRepDto();
        reservationRepDto.setStoreName(reservation.getStore().getStoreName());
        reservationRepDto.setReservationDate(reservation.getReservationDate());
        reservationRepDto.setTotalPrice(reservation.getTotalPrice());
        reservationRepDto.setReservationName(reservation.getReservationName());
        reservationRepDto.setReservationPhone(reservation.getReservationPhone());
        reservationRepDto.setReservationEmail(reservation.getReservationEmail());
        reservationRepDto.setReservationItemRepDtos(reservationItemRepDtos);

        return reservationRepDto;
    }

}
