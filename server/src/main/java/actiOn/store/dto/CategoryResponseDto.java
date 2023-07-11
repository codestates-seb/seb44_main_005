package actiOn.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CategoryResponseDto {
    private List<Map<String, Integer>> pageInfo;

    private List<CategoryStore> data;

    @Getter
    @Setter
    public class CategoryStore {
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
