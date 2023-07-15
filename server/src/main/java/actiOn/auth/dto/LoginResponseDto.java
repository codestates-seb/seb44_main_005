package actiOn.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String role;
    private String nickname;
    private String profileImage;
}
