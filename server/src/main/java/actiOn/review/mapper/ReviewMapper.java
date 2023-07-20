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
        return new ReviewResponseDto(
                review.getMember().getProfileImg(),
                review.getMember().getNickname(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt()
        );
    }

    public List<ReviewResponseDto> reviewsToReviewsResponseDto(List<Review> reviews){
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>(reviews.size());
        for (Review review : reviews){
            reviewResponseDtoList.add(this.reviewToReviewResponseDto(review));
        }
        return reviewResponseDtoList;
    }

}
