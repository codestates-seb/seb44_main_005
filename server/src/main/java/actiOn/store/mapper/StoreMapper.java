package actiOn.store.mapper;


import actiOn.item.entity.Item;
import actiOn.store.dto.StoreIdResponseDto;
import actiOn.store.dto.StorePatchDto;
import actiOn.store.dto.StorePostDto;
import actiOn.store.dto.StoreResponseDto;
import actiOn.store.entity.Store;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface StoreMapper {
    Store storePostDtoToStore(StorePostDto storePostDto);

    Store storePatchDtoToStore(StorePatchDto storePatchDto);

    StoreIdResponseDto storeToStorePostResponseDto(Store store);

    List<Item> storePostDtoItemsToItemEntities(List<Item> itemList);
}
