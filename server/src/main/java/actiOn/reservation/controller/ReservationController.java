package actiOn.reservation.controller;

import actiOn.item.dto.ItemDto;
import actiOn.item.service.ItemService;
import actiOn.payment.entity.Payment;
import actiOn.reservation.dto.ReservationPatchDto;
import actiOn.reservation.dto.ReservationPostDto;
import actiOn.reservation.dto.ReservationRedisResponseDto;
import actiOn.reservation.dto.ReservationResponseDto;
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
@RequestMapping
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
    @PostMapping("/reservations/{store-id}")
    public ResponseEntity postReservation(@Positive @PathVariable("store-id") Long storeId,
                                          @Valid @RequestBody ReservationPostDto requestBody) {
        String reservationKey = reservationService.redisSaveReservation(storeId,requestBody);
        return new ResponseEntity<>(new ReservationRedisResponseDto(reservationKey),HttpStatus.OK);
    }

    @PostMapping("/reservation/payments")
    public ResponseEntity confirmReservationAndPayment(@RequestParam("reservationKey") String reservationKey,
                                                       @RequestParam("orderId") String orderId) {
        ReservationPostDto reservationPostDto = reservationService.getReservationPostDtoFromRedis(reservationKey);
        Reservation reservation = reservationMapper.reservationPostDtoToReservation(reservationPostDto);
        List<ReservationItem> reservationItems = reservationMapper.reservationItemsDtoToReservationItem(reservationPostDto, itemService);
        Payment payment = reservationService.createPaymentByOrderId(orderId);
        reservationService.postReservation(reservationPostDto.getStoreId(),reservation,reservationItems,payment);
        return new ResponseEntity(HttpStatus.CREATED);
    }
//    @PostMapping("/reservations/{store-id}")
//    public ResponseEntity postReservation(@Positive @PathVariable("store-id") Long storeId,
//                                          @Valid @RequestBody ReservationPostDto requestBody) {
//        Reservation reservation = reservationMapper.reservationPostDtoToReservation(requestBody);
//        List<ReservationItem> reservationItems = reservationMapper.reservationItemsDtoToReservationItem(requestBody, itemService);
//        reservationService.postReservation(storeId, reservation, reservationItems);
//        //Todo 내용추가 // 예약자 정보 안적었을 때 500
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    // 예약 수정
    @PatchMapping("/reservations/{reservation-id}")
    public ResponseEntity patchReservation(@Positive @PathVariable("reservation-id") Long reservationId,
                                           @Valid @RequestBody ReservationPatchDto reservationPatchDto) {
        Reservation updateReservation = reservationMapper.reservationPatchDtoToReservation(reservationPatchDto);
        reservationService.updateReservation(reservationId, updateReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Todo RS_001 예약 수정 페이지 렌더(완료, 테스트 필요)
    @GetMapping("/reservations/{reservation-id}")
    public ResponseEntity getReservations(@Positive @PathVariable("reservation-id") Long reservationId) {
        Reservation findReservation = reservationService.getReservations(reservationId);
        ReservationResponseDto response = reservationMapper.reservationToReservationRepDto(findReservation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Todo RS_004 예약 취소(완료, 테스트 필요)
    @DeleteMapping("/reservations/{reservation-id}")
    public ResponseEntity cancelReservation(@Positive @PathVariable("reservation-id") long reservationId) {
        reservationService.cancelReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/items/{store-id}") // 그 스토어, 그 날짜에 티켓 상태 조회
    public ResponseEntity getStoreByDate(@PathVariable("store-id") @Positive long storeId,
                                         @RequestParam("date") String date) {
        List<ItemDto> items = reservationService.findItemsByStoreIdAndDate(storeId, date);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/reservationsUsed/{reservation-id}")
    public ResponseEntity doneReservation(@PathVariable("reservation-id")@Positive Long reservationId){
        reservationService.changeReservationStatus(reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/reservation/save/{store-id}")
//    public ResponseEntity postReservationToRedis(@PathVariable("store-id") long storeId,
//                                                 @RequestBody ReservationPostDto reservationPostDto) {
//        reservationService.
//    }

}
