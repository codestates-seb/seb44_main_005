package actiOn.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class ReviewPostDto {
    @NotBlank
    private String content;

    @Range(min = 0, max = 5)
    private Integer rating;
}
