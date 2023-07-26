package actiOn.Img.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileImgDto {
    private String nickname;
    private String profileImage;
}
