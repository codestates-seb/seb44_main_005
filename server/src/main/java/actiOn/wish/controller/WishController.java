package actiOn.wish.controller;

import actiOn.wish.mapper.WishMapper;
import actiOn.wish.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
public class WishController {

    private final WishService wishService;
    private final WishMapper wishMapper;

    public WishController(WishService wishService, WishMapper wishMapper) {
        this.wishService = wishService;
        this.wishMapper = wishMapper;
    }

    //TODO CP_002 찜 등록
    @PostMapping("/stores/favorites/{store-id}")
    public ResponseEntity registerWish(@Positive @PathVariable("store-id") Long storeId){
        wishService.registerWish(storeId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //TODO CP_002 찜 취소
    @DeleteMapping("/stores/favorites/{store-id}")
    public ResponseEntity cancelWish(@Positive @PathVariable("store-id") Long storeId){
        wishService.deleteWish(storeId);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
