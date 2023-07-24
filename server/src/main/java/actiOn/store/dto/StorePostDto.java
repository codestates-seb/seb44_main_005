package actiOn.store.dto;

import actiOn.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class StorePostDto {
    @NotBlank(message = "store name을 작성해주세요")
    private String storeName;

    @NotBlank(message = "소개글을 작성해주세요")
    private String body;

    @NotBlank(message = "주소를 작성해주세요")
    private String address;

    @NotBlank(message = "카카오 아이디를 작성해주세요")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "영문과 숫자만 입력 가능합니다.")
    private String kakao;

    @NotBlank(message = "연락처를 작성해주세요")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",
            message = "연락처 형식이 잘못되었습니다.")
    private String contact;

    @NotBlank(message = "카테고리를 선택해주세요")
    private String category;

    @NotEmpty(message = "상품목록을 작성해주세요")
    @Size(min = 1, message = "상품목록은 최소 1개 이상 작성해야 합니다.")
    private List<Item> items;

}
