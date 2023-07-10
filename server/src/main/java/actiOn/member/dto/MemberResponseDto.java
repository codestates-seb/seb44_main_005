package actiOn.member.dto;

import actiOn.Img.profileImg.ProfileImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private String nickname;
    private String email;
    private String phoneNumber;
    private String profileImg;
}
