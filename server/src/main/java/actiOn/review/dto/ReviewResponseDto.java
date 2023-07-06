package actiOn.review.dto;

import actiOn.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponseDto {
    private String profileImg;
    private String nickname;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;

    public ReviewResponseDto(String profileImg, String nickname, String content, Integer rating, LocalDateTime createdAt) {
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}
