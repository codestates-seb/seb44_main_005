package actiOn.wish.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class WishlistResponseDto {
    private List<WishStoreDto> stores;

    @Getter
    @Setter
    @Builder
    public static class WishStoreDto {
        private long storeId;
        private String category;
        private String title;
        private String content;
        private String address;
        private double rating;
        private int reviewCount;
        private int price;
        private Boolean isLike;
        private String img;
    }
}
