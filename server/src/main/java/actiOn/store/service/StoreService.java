package actiOn.store.service;

import actiOn.exception.BusinessLogicException;
import actiOn.item.entity.Item;
import actiOn.map.response.GeoLocation;
import actiOn.map.service.KakaoMapService;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final KakaoMapService kakaoMapService;
    public StoreService(StoreRepository storeRepository, KakaoMapService kakaoMapService){
        this.storeRepository=storeRepository;
        this.kakaoMapService = kakaoMapService;
    }
    public Store createStore(Store store){ // store를 받아서, 주소를 가져온 다음, 그 주소를 카카오로 보내서 좌표를 받아옴
        GeoLocation location = kakaoMapService.addressToLocation(store.getAddress());
        store.setLatitude(Double.parseDouble(location.getLatitude()));
        store.setLongitude(Double.parseDouble(location.getLongitude()));
        List<Item> items = store.getItems();
        for (Item item : items){
            item.setStore(store);
        }
        return storeRepository.save(store);
    }

    public Optional<Store> findStoreByStoreId(long storeId) throws BusinessLogicException {
        return storeRepository.findById(storeId);
    }


}
