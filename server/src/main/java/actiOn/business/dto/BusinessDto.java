package actiOn.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BusinessDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "사업자 명을 입력해주세요.")
        private String owner;

        @NotBlank(message = "사업명을 입력해주세요.")
        private String businessName;

        @NotBlank(message = "사업자 등록번호를 입력해주세요.")
        @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}",
                message = "사업자 등록번호는 000-00-00000 형식으로 하이픈 '-'을 포함하여 작성해주세요.")
        private String registrationNumber;

        @NotBlank(message = "업종을 선택해주세요.")
        private String businessCategory;
    }
}
