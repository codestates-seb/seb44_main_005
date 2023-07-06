package actiOn.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ReviewsResponseDto {
    private int reviewCount;
    private List<ReviewResponseDto> reviewResponseDtoList;
}
