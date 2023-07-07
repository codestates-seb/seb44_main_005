package actiOn.review.controller;

import actiOn.review.dto.ReviewDto;
import actiOn.review.mapper.ReviewMapper;
import actiOn.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    //TODO RV_001 리뷰 작성
    @PostMapping("/reviews/{store-id}")
    public ResponseEntity createReview(@Positive @PathVariable("store-id") Long storeId,
                                       @Valid @RequestBody ReviewDto reviewDto){
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/review/{store-id}")
    public ResponseEntity getAllReviews(@Positive @PathVariable("store-id") Long storeId){
        return new ResponseEntity(HttpStatus.OK);
    }
}
