package actiOn.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewResponseDto {
    private int reviewCount;
    private double ratingAvg;
    private List<ReviewDto> reviews;

    @Getter
    @Builder
    public static class ReviewDto {
        private String nickname;
        private String content;
        private Integer rating;
        private LocalDateTime createdAt;
    }
}
