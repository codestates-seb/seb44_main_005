package actiOn.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CategoryResponseDto {
    private List<Map<String, Integer>> pageInfo;

    private List<CategoryStoreDto> data;
}
