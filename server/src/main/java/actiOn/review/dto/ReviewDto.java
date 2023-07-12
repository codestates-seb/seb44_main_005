package actiOn.review.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class ReviewDto {

    @NotBlank
    @Pattern(regexp = "\\S+", message = "공백은 허용되지 않습니다.")
    private String content;

    @Range(min = 0, max = 5)
    private Integer rating;
}
