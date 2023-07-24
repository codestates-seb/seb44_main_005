package actiOn.review.mapper;

import actiOn.review.dto.ReviewPostDto;
import actiOn.review.dto.ReviewResponseDto;
import actiOn.review.entity.Review;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ReviewMapper {

    Review reviewPostDtoToReview(ReviewPostDto reviewPostDto);

    default ReviewResponseDto reviewsToReviewResponseDto(List<Review> reviewList, double storeRating) {
        List<ReviewResponseDto.ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : reviewList) {
            reviewDtos.add(reviewToReviewDto(review));
        }

        return ReviewResponseDto.builder()
                .reviewCount(reviewList.size())
                .ratingAvg(storeRating)
                .reviews(reviewDtos)
                .build();
    }

    default ReviewResponseDto.ReviewDto reviewToReviewDto(Review review) {
        ReviewResponseDto.ReviewDto.ReviewDtoBuilder builder =
                ReviewResponseDto.ReviewDto.builder();

        builder.nickname(review.getMember().getNickname());
        builder.content(review.getContent());
        builder.rating(review.getRating());
        builder.createdAt(review.getCreatedAt());

        return builder.build();
    }
}
