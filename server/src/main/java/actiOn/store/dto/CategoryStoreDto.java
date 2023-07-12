package actiOn.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryStoreDto {
    private long storeId;
    private String category;
    private String title;
    private String content;
    private String address;
    private double rating;
    private int reviewCount;
    private int price;
    private Boolean isLike = false;
    private String img;

}
