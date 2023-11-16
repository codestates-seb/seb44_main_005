package actiOn.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class PartnerStoreResponseDto {
    private List<PartnerStoreDto> stores;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PartnerStoreDto {
        private Long storeId;
        private String storeImage;
        private String storeName;
    }
}
