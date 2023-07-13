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
        System.out.println("체크포인트1");
        reservation.setReservationName(reservationReqDto.getReservationName());
        System.out.println("체크포인트2");
        reservation.setReservationPhone(reservationReqDto.getReservationPhone());
        System.out.println("체크포인트3");
        reservation.setReservationEmail(reservationReqDto.getReservationEmail());
        System.out.println("체크포인트4");
        reservation.setReservationDate(LocalDate.parse(reservationReqDto.getReservationDate()));
        System.out.println("체크포인트5");
        reservation.setTotalPrice(reservationReqDto.getTotalPrice());
        System.out.println("체크포인트6");

        List<ReservationItemReqDto> itemReqDtos = reservationReqDto.getReservationItemReqDtos();
        System.out.println("체크포인트7");
        List<ReservationItem> reservationItems = new ArrayList<>();
        System.out.println("체크포인트8");
        for (ReservationItemReqDto itemReqDto : itemReqDtos) {
            System.out.println("체크포인트9");
            ReservationItem reservationItem = new ReservationItem();
            System.out.println("체크포인트10");
            reservationItem.setTicketCount(itemReqDto.getTicketCount());
            System.out.println("체크포인트11");

            //Item 객체 매핑
            Item item = new Item();
            item.setItemId(itemReqDto.getItemId());
            System.out.println("체크포인트12");
            reservationItem.setItem(item);
            System.out.println("체크포인트12");

            reservationItems.add(reservationItem);
            System.out.println("체크포인트14");
        }
        reservation.setReservationItems(reservationItems);
        System.out.println("체크포인트15");
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
