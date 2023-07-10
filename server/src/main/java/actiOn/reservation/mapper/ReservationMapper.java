package actiOn.reservation.mapper;

import actiOn.item.entity.Item;
import actiOn.member.entity.Member;
import actiOn.reservation.dto.ReservationItemReqDto;
import actiOn.reservation.dto.ReservationReqDto;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationMapper {

    public Reservation reservationReqDtoToReservation(ReservationReqDto reservationReqDto) {
        Reservation reservation = new Reservation();
        reservation.setReservationName(reservationReqDto.getReservationName());
        reservation.setReservationPhone(reservationReqDto.getReservationPhone());
        reservation.setReservationEmail(reservationReqDto.getReservationEmail());
        reservation.setReservationDate(reservationReqDto.getReservationDate());
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

//    public Reservation

}
