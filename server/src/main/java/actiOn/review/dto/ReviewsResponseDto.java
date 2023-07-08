package actiOn.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Builder
public class ReviewsResponseDto {
    private int reviewCount;
    private double ratingAvg;
    private List<ReviewResponseDto> reviewResponseDtoList;
}
