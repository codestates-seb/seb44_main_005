package actiOn.reservation.controller;

import actiOn.reservation.dto.ReservationPatchDto;
import actiOn.reservation.dto.ReservationReqDto;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.mapper.ReservationMapper;
import actiOn.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    //Todo RS_003 예약 수정
    @PatchMapping("/{store-id}")
    public ResponseEntity patchReservation(@Positive @PathVariable("store-id") Long storeId,
                                           @Valid @RequestBody ReservationPatchDto reservationPatchDto){
//        reservationService.updateReservation(storeId,reservationPatchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Todo RS_001 예약 수정 페이지 렌더
    @GetMapping("/{store-id}")
    public ResponseEntity getReservations(@Positive @PathVariable("store-id") Long storeId,
                                          Authentication authentication){
        //Todo 내용추가
//        reservationService.getReservations(storeId,authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 내 예약 취소 -> 예약이 취소된다고 삭제하지 말고 상태변경
    // Todo 예약내역 수정이랑 엔드포인트가 겹쳐서 Patch로 하기도 좀 그럼// 예약 취소를 delete method로 받는건 안좋은듯?
    @DeleteMapping("/{store-id}")
    public ResponseEntity cancelReservation(@Positive @PathVariable("store-id") long storeId){
        //Todo Authentication authentication 을 시그니처에 추가
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
