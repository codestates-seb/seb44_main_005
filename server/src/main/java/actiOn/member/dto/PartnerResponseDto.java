package actiOn.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PartnerResponseDto {
    private List<StoreDto> stores;

    @Builder
    @Getter
    @Setter
    public static class StoreDto {
        private String category;
        private String storeName;
    }
}
