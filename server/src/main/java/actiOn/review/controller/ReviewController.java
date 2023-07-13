package actiOn.review.controller;

import actiOn.review.dto.ReviewDto;
import actiOn.review.dto.ReviewsResponseDto;
import actiOn.review.entity.Review;
import actiOn.review.mapper.ReviewMapper;
import actiOn.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
        Review review = reviewMapper.reviewDtoToReview(reviewDto);
        reviewService.createReview(storeId,review);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //TODO RV_001 리뷰 페이지
    @GetMapping("/reviews/{store-id}")
    public ResponseEntity getAllReviews(@Positive @PathVariable("store-id") Long storeId){
        ReviewsResponseDto reviewsResponseDto = reviewService.getAllReviews(storeId);
        return new ResponseEntity(reviewsResponseDto,HttpStatus.OK);
    }
}
