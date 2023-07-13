package actiOn.review.mapper;

import actiOn.review.dto.ReviewDto;
import actiOn.review.dto.ReviewResponseDto;
import actiOn.review.entity.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    public Review reviewDtoToReview(ReviewDto reviewDto){
        return new Review(
                reviewDto.getContent(),
                reviewDto.getRating()
        );
    }

    public ReviewResponseDto reviewToReviewResponseDto(Review review){
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
        try {
            reviewResponseDto.setProfileImg(review.getMember().getProfileImg().getLink());
        }catch (NullPointerException e){
            reviewResponseDto.setProfileImg("현재 프로필 이미지 없음");
        }
        reviewResponseDto.setNickname(review.getMember().getNickname());
        reviewResponseDto.setContent(review.getContent());
        reviewResponseDto.setRating(review.getRating());
        reviewResponseDto.setCreatedAt(review.getCreatedAt());

        return reviewResponseDto;
    }

    public List<ReviewResponseDto> reviewsToReviewsResponseDto(List<Review> reviews){
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>(reviews.size());
        for (Review review : reviews){
            reviewResponseDtoList.add(this.reviewToReviewResponseDto(review));
        }
        return reviewResponseDtoList;
    }

}
