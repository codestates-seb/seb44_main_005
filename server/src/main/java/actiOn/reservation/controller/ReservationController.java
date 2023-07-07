package actiOn.reservation.controller;

import actiOn.reservation.dto.ReservationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PostMapping("/{store-id}") // 예약 등록
    public ResponseEntity postReservation(@Positive @PathVariable("store-id") long storeId,
                                          @Valid @RequestBody ReservationDto.PostDto reservationPost){
        //Todo Authentication authentication 을 시그니처에 추가
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{store-id}") // 내 예약 수정
    public ResponseEntity patchReservation(@Positive @PathVariable("store-id") long storeId,
                                           @Valid @RequestBody ReservationDto.PatchDto reservationPatch){
        //Todo Authentication authentication 을 시그니처에 추가
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{store-id}") // 내 예약내역 조회
    public ResponseEntity getReservations(@Positive @PathVariable("store-id") long storeId){
        //Todo Authentication authentication 을 시그니처에 추가
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{store-id}") // 내 예약 취소 -> 예약이 취소된다고 삭제하지 말고 상태변경 //Todo 예약내역 수정이랑 엔드포인트가 겹쳐서 Patch로 하기도 좀 그럼// 예약 취소를 delete method로 받는건 안좋은듯?
    public ResponseEntity cancelReservation(@Positive @PathVariable("store-id") long storeId){
        //Todo Authentication authentication 을 시그니처에 추가
        //Todo 내용추가
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
