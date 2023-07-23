package actiOn.store.mapper;


import actiOn.Img.storeImg.StoreImg;
import actiOn.store.dto.CategoryResponseDto;
import actiOn.store.dto.CategoryStoreDto;
import actiOn.store.entity.Store;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryResponseMapper {

    public CategoryResponseDto CategoryStoreToCategoryResponseDto(List<Store> categoryStores) {
        //categoryStores는 이미 정렬되어 있음에 주의
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        List<CategoryStoreDto> categoryStoreDtos = new ArrayList<>();

        //받아온 카테고리 스토어 리스트를 순회하면서 categoryStoreDto에 담아주고, 리스트로 만든 다음, responseDto 형식으로 매핑
        for (Store originStore : categoryStores) {
            CategoryStoreDto categoryStoreDto = new CategoryStoreDto();

            //여기서부터 이미지 관련
            String img = originStore.getStoreImgList().isEmpty() ? null : originStore.getStoreImgList().get(0).getLink();

            for (StoreImg storeImg : originStore.getStoreImgList()) {
                if (storeImg.getIsThumbnail()) {
                    img = storeImg.getLink();
                    break;
                }
            }

            if (img == null) {
                throw new IllegalArgumentException("스토어의 이미지가 존재하지 않습니다.");
            }

            categoryStoreDto.setImg(img);
            categoryStoreDto.setStoreId(originStore.getStoreId());
            categoryStoreDto.setCategory(originStore.getCategory());
            categoryStoreDto.setTitle(originStore.getStoreName());
            categoryStoreDto.setContent(originStore.getBody());
            categoryStoreDto.setAddress(originStore.getAddress());
            categoryStoreDto.setRating(originStore.getRating());
            categoryStoreDto.setReviewCount(originStore.getReviewCount());
            categoryStoreDto.setPrice(originStore.getLowPrice());

            categoryStoreDtos.add(categoryStoreDto);
        }

        int storeCount = categoryStoreDtos.size();

        Map<String, Integer> storeCountMap = new HashMap<>();
        storeCountMap.put("storeCount", storeCount);

        List<Map<String, Integer>> pageInfo = new ArrayList<>();
        pageInfo.add(storeCountMap);

        categoryResponseDto.setPageInfo(pageInfo);
        categoryResponseDto.setData(categoryStoreDtos);

        return categoryResponseDto;
    }
}
