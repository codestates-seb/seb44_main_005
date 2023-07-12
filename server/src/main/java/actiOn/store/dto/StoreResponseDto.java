package actiOn.store.dto;


import actiOn.item.dto.ItemDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StoreResponseDto {
    private long StoreId;
    private String storeName;
    private String category;
    private String body;
    private double latitude;
    private double longitude;
    private String kakao;
    private String contact;
    private String address;
    private Boolean isLike = false;
    private String profileImg;
    private LocalDateTime createdAt;
    private List<ItemDto> items;
    private List<String> storeImages;

//    public boolean getIsLike() {
//        return isLike;
//    }
//
//    public void setIsLike(boolean isLike) {
//        isLike = isLike;
//    }
}
