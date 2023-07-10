package actiOn.reservation.controller;

import actiOn.reservation.dto.request.ReservationPatchDto;
import actiOn.reservation.dto.request.ReservationReqDto;
import actiOn.reservation.dto.response.ReservationRepDto;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.mapper.ReservationMapper;
import actiOn.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    //Todo RS_002 예약 등록(완료 직전, 테스트 필요)
    @PostMapping("/{store-id}")
    public ResponseEntity postReservation(@Positive @PathVariable("store-id") Long storeId,
                                          @Valid @RequestBody ReservationReqDto reservationReqDto
                                          ){
        Reservation reqReservation = reservationMapper.reservationReqDtoToReservation(reservationReqDto);
        reservationService.postReservation(storeId, reqReservation);
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Todo RS_003 예약 수정(완료, 테스트 필요)
    @PatchMapping("/{reservation-id}")
    public ResponseEntity patchReservation(@Positive @PathVariable("reservation-id") Long reservationId,
                                           @Valid @RequestBody ReservationPatchDto reservationPatchDto){
        Reservation updateReservation = reservationMapper.reservationPatchDtoToReservation(reservationPatchDto);
        reservationService.updateReservation(reservationId,updateReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Todo RS_001 예약 수정 페이지 렌더(완료, 테스트 필요)
    @GetMapping("/{reservation-id}")
    public ResponseEntity getReservations(@Positive @PathVariable("reservation-id") Long reservationId){
        Reservation findReservation = reservationService.getReservations(reservationId);
        ReservationRepDto reservationRepDto = reservationMapper.reservationToReservationRepDto(findReservation);
        return new ResponseEntity<>(reservationRepDto,HttpStatus.OK);
    }

    //Todo RS_004 예약 취소(완료, 테스트 필요)
    @DeleteMapping("/{reservation-id}")
    public ResponseEntity cancelReservation(@Positive @PathVariable("reservation-id") long reservationId){
        reservationService.cancelReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
