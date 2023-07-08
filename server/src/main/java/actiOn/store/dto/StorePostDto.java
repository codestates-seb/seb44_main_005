package actiOn.store.dto;

import actiOn.item.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
public class StorePostDto {
    @NotBlank(message = "store name을 작성해주세요")
    private String storeName;

    @NotBlank(message = "소개글을 작성해주세요")
    private String body;

    @NotBlank(message = "주소를 작성해주세요")
    private String address;

    @NotBlank(message = "카카오 아이디를 작성해주세요")
    private String kakao;

    @NotBlank(message = "연락처를 작성해주세요")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",
            message = "연락처 형식이 잘못되었습니다.")
    private String contact;

    @NotBlank(message = "카테고리를 선택해주세요")
    private String category;

    @NotEmpty(message = "상품목록을 작성해주세요")
    private List<Item> items;

    @NotEmpty(message = "이미지를 업로드 해주세요")
    private List<MultipartFile> storeImage;


}
