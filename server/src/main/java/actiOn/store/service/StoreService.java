package actiOn.store.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.map.response.GeoLocation;
import actiOn.map.service.KakaoMapService;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import actiOn.reservation.repository.ReservationRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final KakaoMapService kakaoMapService;

    private final ReservationRepository reservationRepository;

    public StoreService(StoreRepository storeRepository, KakaoMapService kakaoMapService, ReservationRepository reservationRepository){
        this.storeRepository=storeRepository;
        this.kakaoMapService = kakaoMapService;
        this.reservationRepository = reservationRepository;
    }
    public Store createStore(Store store){ // store를 받아서, 주소를 가져온 다음, 그 주소를 카카오로 보내서 좌표를 받아옴
        GeoLocation location = kakaoMapService.addressToLocation(store.getAddress());
        store.setLatitude(Double.parseDouble(location.getLatitude()));
        store.setLongitude(Double.parseDouble(location.getLongitude()));
        List<Item> items = store.getItems();
        int lowPrice = 0;
        for (Item item : items){
            item.setStore(store);
            int itemPrice = item.getPrice();
            if (lowPrice == 0 || itemPrice < lowPrice) lowPrice=itemPrice;
        }
        store.setLowPrice(lowPrice); // 0으로 나오는 문제 해결 필요
        return storeRepository.save(store);
    }

    public Store findStoreByStoreId(long storeId){
        return storeRepository.findById(storeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
    }

    public List<Store> findStoreByCategory(String category, String sortFiled) {
        if (sortFiled.isEmpty()){
            sortFiled = "rating";
        }

        if (category.equals("all") || category.isEmpty()){
            return storeRepository.findAll(Sort.by(Sort.Direction.DESC, sortFiled));
        }
        if (sortFiled.equals("lowPrice")){
            return storeRepository.findByCategory(category, Sort.by(Sort.Direction.ASC, sortFiled)); // 오름차순
        }

        return storeRepository.findByCategory(category, Sort.by(Sort.Direction.DESC, sortFiled)); // 내림차순


    }
}
