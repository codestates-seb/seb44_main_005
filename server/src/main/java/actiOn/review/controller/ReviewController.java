package actiOn.review.controller;

import actiOn.review.dto.ReviewPostDto;
import actiOn.review.dto.ReviewResponseDto;
import actiOn.review.entity.Review;
import actiOn.review.mapper.ReviewMapper;
import actiOn.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    // 리뷰 작성
    @PostMapping("/reviews/{store-id}")
    public ResponseEntity createReview(@Positive @PathVariable("store-id") Long storeId,
                                       @Valid @RequestBody ReviewPostDto requestBody) {
        Review review = reviewMapper.reviewPostDtoToReview(requestBody);
        reviewService.createReview(storeId, review);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 리뷰 페이지 렌더
    @GetMapping("/reviews/{store-id}")
    public ResponseEntity getAllReviews(@Positive @PathVariable("store-id") Long storeId) {
        List<Review> reviewList = reviewService.getAllReviews(storeId);
        double storeRating = reviewService.getStoreAvgRating(storeId);

        ReviewResponseDto reviewResponseDto =
                reviewMapper.reviewsToReviewResponseDto(reviewList, storeRating);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }
}
