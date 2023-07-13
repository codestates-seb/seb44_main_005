package actiOn.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberPostDto {
    @Getter
    @AllArgsConstructor
    public static class Signup {
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*\\W).{8,20}",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 작성해주세요.")
        private String password;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "^(010|011)-(?:\\d{3}|\\d{4})-\\d{4}$",
                message = "휴대폰 번호는 010(혹은 011)만 가능하며, 하이픈 '-'을 포함하여 작성해주세요.")
        private String phoneNumber;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,}$",
                message = "닉네임은 영문과 숫자만 가능하며, 4자 이상 작성해주세요.")
        private String nickname;
    }
}