package actiOn.store.dto.mainrep;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataDto {
    private Long storeId;
    private String storeName;
    private double latitude;
    private double longitude;
    private String category;
}
