package actiOn.reservation.mapper;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.item.service.ItemService;
import actiOn.reservation.dto.*;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ReservationMapper {
    // 예약 등록
    default Reservation reservationPostDtoToReservation(ReservationPostDto requestBody) {
        Reservation reservation = new Reservation(
                requestBody.getReservationName(),
                requestBody.getReservationPhone(),
                requestBody.getReservationEmail()
        );

        reservation.setTotalPrice(requestBody.getTotalPrice());
        LocalDate date;
        try{
            date = LocalDate.parse(requestBody.getReservationDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (DateTimeParseException e) {
            throw new BusinessLogicException(ExceptionCode.DATE_BAD_REQUEST);
        }
        reservation.setReservationDate(date);

        return reservation;
    }

    default List<ReservationItem> reservationItemsDtoToReservationItem(
            ReservationPostDto requestBody, ItemService itemService) {

        List<ReservationItemDto> reservationItemDtos =
                requestBody.getReservationItems();

        List<ReservationItem> reservationItems = new ArrayList<>();
        for (ReservationItemDto reservationItemDto : reservationItemDtos) {
            reservationItems.add(
                    reservationItemDtoToReservationItem(reservationItemDto, itemService)
            );
        }

        return reservationItems;
    }

    default ReservationItem reservationItemDtoToReservationItem(
            ReservationItemDto reservationItemDto, ItemService itemService) {
        ReservationItem reservationItem = new ReservationItem(reservationItemDto.getTicketCount());

        Item item = itemService.findItem(reservationItemDto.getItemId());
        reservationItem.setItem(item);

        return reservationItem;
    }

    // 예약 수정
    default Reservation reservationPatchDtoToReservation(ReservationPatchDto reservationPatchDto) {
        return new Reservation(
                reservationPatchDto.getReservationName(),
                reservationPatchDto.getReservationPhone(),
                reservationPatchDto.getReservationEmail()
        );
    }

    // 예약 페이지 렌더링
    default ReservationResponseDto reservationToReservationRepDto(Reservation reservation) {
        ReservationResponseDto.ReservationResponseDtoBuilder builder
                = ReservationResponseDto.builder();

        builder.storeName(reservation.getStore().getStoreName())
                .reservationDate(reservation.getReservationDate())
                .totalPrice(reservation.getTotalPrice())
                .reservationName(reservation.getReservationName())
                .reservationPhone(reservation.getReservationPhone())
                .reservationEmail(reservation.getReservationEmail())
                .reservationItems(
                        reservationItemsToResonseDtos(reservation.getReservationItems())
                );

        return builder.build();
    }

    default List<ReservationItemResponseDto> reservationItemsToResonseDtos(List<ReservationItem> reservationItems) {
        List<ReservationItemResponseDto> reservationItemDtos = new ArrayList<>();

        for (ReservationItem reservationItem : reservationItems) {
            reservationItemDtos.add(reservationItemToResponseDto(reservationItem));
        }

        return reservationItemDtos;
    }

    default ReservationItemResponseDto reservationItemToResponseDto(ReservationItem reservationItem) {
        ReservationItemResponseDto.ReservationItemResponseDtoBuilder builder =
                ReservationItemResponseDto.builder();

        builder.itemName(reservationItem.getItem().getItemName())
                .ticketCount(reservationItem.getTicketCount());

        return builder.build();
    }
}
