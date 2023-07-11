package actiOn.store.mapper;


import actiOn.Img.storeImg.StoreImg;
import actiOn.store.dto.CategoryResponseDto;
import actiOn.store.dto.CategoryStoreDto;
import actiOn.store.entity.Store;
import org.springframework.stereotype.Component;

import javax.xml.catalog.Catalog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryResponseMapper {

    public CategoryResponseDto CategoryStoreToCategoryResponseDto (List<Store> categoryStores) {
        //categoryStores는 이미 정렬되어 있음에 주의
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        List<CategoryStoreDto> categoryStoreDtos = new ArrayList<>();

        //받아온 카테고리 스토어 리스트를 순회하면서 categoryStoreDto에 담아주고, 리스트로 만든 다음, responseDto 형식으로 매핑
        for (Store originStore : categoryStores) {
            CategoryStoreDto categoryStoreDto = new CategoryStoreDto();

            //여기서부터 이미지 관련
            categoryStoreDto.setImg(originStore.getStoreImgList().get(0).getLink());  // 카테고리가 없는 경우 기본 값->0번째 사진

            for (StoreImg storeImg : originStore.getStoreImgList()){
                if (storeImg.getIsThumbnail()){
                    categoryStoreDto.setImg(storeImg.getLink());
                }
            }
            //위에까지가 이미지 관련

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
