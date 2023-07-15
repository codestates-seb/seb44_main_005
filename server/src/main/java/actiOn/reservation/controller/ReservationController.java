package actiOn.reservation.controller;

import actiOn.item.dto.ItemDto;
import actiOn.item.service.ItemService;
import actiOn.reservation.dto.ReservationPostDto;
import actiOn.reservation.dto.request.ReservationPatchDto;
import actiOn.reservation.dto.response.ReservationRepDto;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import actiOn.reservation.mapper.ReservationMapper;
import actiOn.reservation.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final ItemService itemService;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper, ItemService itemService) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
        this.itemService = itemService;
    }

    //Todo RS_002 예약 등록(완료 직전, 테스트 필요)
    @PostMapping("/{store-id}")
    public ResponseEntity postReservation(@Positive @PathVariable("store-id") Long storeId,
                                          @Valid @RequestBody ReservationPostDto requestBody) {
        Reservation reservation = reservationMapper.reservationPostDtoToReservation(requestBody);
        List<ReservationItem> reservationItems = reservationMapper.reservationItemsDtoToReservationItem(requestBody, itemService);
        reservationService.postReservation(storeId, reservation, reservationItems);
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 예약 수정(완료, 테스트 필요)
    @PatchMapping("/{reservation-id}")
    public ResponseEntity patchReservation(@Positive @PathVariable("reservation-id") Long reservationId,
                                           @Valid @RequestBody ReservationPatchDto reservationPatchDto) {
        Reservation updateReservation = reservationMapper.reservationPatchDtoToReservation(reservationPatchDto);
        reservationService.updateReservation(reservationId, updateReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Todo RS_001 예약 수정 페이지 렌더(완료, 테스트 필요)
    @GetMapping("/{reservation-id}")
    public ResponseEntity getReservations(@Positive @PathVariable("reservation-id") Long reservationId) {
        Reservation findReservation = reservationService.getReservations(reservationId);
        ReservationRepDto reservationRepDto = reservationMapper.reservationToReservationRepDto(findReservation);
        return new ResponseEntity<>(reservationRepDto, HttpStatus.OK);
    }

    //Todo RS_004 예약 취소(완료, 테스트 필요)
    @DeleteMapping("/{reservation-id}")
    public ResponseEntity cancelReservation(@Positive @PathVariable("reservation-id") long reservationId) {
        reservationService.cancelReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/items/{store-id}") // 그 스토어, 그 날짜에 티켓 상태 조회
    public ResponseEntity getStoreByDate(@PathVariable("store-id") @Positive long storeId,
                                         @RequestParam("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {

        List<ItemDto> items = reservationService.findItemsByStoreIdAndDate(storeId, date);
        if (items != null) {
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
        return new ResponseEntity<>("예약정보를 불러오는데 실패했습니다.", HttpStatus.NOT_FOUND);
    }
}
