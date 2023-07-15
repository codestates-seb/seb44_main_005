package actiOn.reservation.mapper;

import actiOn.item.entity.Item;
import actiOn.item.service.ItemService;
import actiOn.reservation.dto.ReservationPostDto;
import actiOn.reservation.dto.request.ReservationPatchDto;
import actiOn.reservation.dto.response.ReservationItemRepDto;
import actiOn.reservation.dto.response.ReservationRepDto;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ReservationMapper {

    default Reservation reservationPostDtoToReservation(ReservationPostDto requestBody) {
        Reservation reservation = new Reservation(
                requestBody.getReservationName(),
                requestBody.getReservationPhone(),
                requestBody.getReservationEmail()
        );

        reservation.setTotalPrice(requestBody.getTotalPrice());
        reservation.setReservationDate(LocalDate.parse(requestBody.getReservationDate()));

        return reservation;
    }

    default List<ReservationItem> reservationItemsDtoToReservationItem(
            ReservationPostDto requestBody, ItemService itemService) {

        List<ReservationPostDto.ReservationItemDto> reservationItemDtos =
                requestBody.getReservationItems();

        List<ReservationItem> reservationItems = new ArrayList<>();
        for (ReservationPostDto.ReservationItemDto reservationItemDto : reservationItemDtos) {
            reservationItems.add(
                    reservationItemDtoToReservationItem(reservationItemDto, itemService)
            );
        }

        return reservationItems;
    }

    default ReservationItem reservationItemDtoToReservationItem(
            ReservationPostDto.ReservationItemDto reservationItemDto, ItemService itemService) {
        ReservationItem reservationItem = new ReservationItem(reservationItemDto.getTicketCount());

        Item item = itemService.findItem(reservationItemDto.getItemId());
        reservationItem.setItem(item);

        return reservationItem;
    }

    default Reservation reservationPatchDtoToReservation(ReservationPatchDto reservationPatchDto) {
        return new Reservation(
                reservationPatchDto.getReservationName(),
                reservationPatchDto.getReservationPhone(),
                reservationPatchDto.getReservationEmail()
        );
    }

    default ReservationRepDto reservationToReservationRepDto(Reservation reservation) {
        List<ReservationItemRepDto> reservationItemRepDtos = new ArrayList<>();
        List<ReservationItem> reservationItems = reservation.getReservationItems();
        for (ReservationItem reservationItem : reservationItems) {
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
