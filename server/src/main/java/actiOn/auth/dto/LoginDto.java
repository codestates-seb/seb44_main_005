package actiOn.auth.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class LoginDto {
    @NotNull
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*\\W).{8,20}",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 작성해주세요.")
    private String password;
}
