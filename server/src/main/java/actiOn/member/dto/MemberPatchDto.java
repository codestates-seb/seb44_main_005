package actiOn.member.dto;

import actiOn.helper.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class MemberPatchDto {
    @NotSpace(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$",
            message = "닉네임은 영문과 숫자만 가능하며, 4자 이상 작성해주세요.")
    private String nickname;

    @NotSpace(message = "휴대폰 번호를 입력해주세요")
    @Pattern(regexp = "^(010|011)-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "휴대폰 번호는 010(혹은 011)만 가능하며, 하이픈 '-'을 포함하여 작성해주세요.")
    private String phoneNumber;

    public void setMemberId(long memberId) {

    }
}
