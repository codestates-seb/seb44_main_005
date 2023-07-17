package actiOn.store.dto.mainrep;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RecommendDto {
    private Long storeId;
    private String storeName;
    private String body;
    private String img;
}
