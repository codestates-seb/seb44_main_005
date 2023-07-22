package actiOn.store.mapper;

import actiOn.Img.storeImg.StoreImg;
import actiOn.item.dto.ItemDto;
import actiOn.item.entity.Item;
import actiOn.reservation.service.ReservationService;
import actiOn.store.dto.StoreResponseDto;
import actiOn.store.entity.Store;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StoreResponseMapper {
    private final ReservationService reservationService;

    public StoreResponseMapper(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public StoreResponseDto storeToStoreResponseDto(Store store) {
        List<Item> findItems = store.getItems();
        List<ItemDto> modifiedItems = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        Map<Long, Integer> reservationTicketCountInfo = reservationService.reservationTicketCount(store, currentDate);
        for (Item item : findItems) {
            if (item.getStatus().equals(Item.ItemStatus.DELETED)) continue;
            long itemId = item.getItemId();
            int maxCount = item.getTotalTicket();
            int remainingTicketCount = maxCount;
            if (reservationTicketCountInfo.size() != 0) {
                int reservationTicketCount;
                try{
                    reservationTicketCount = reservationTicketCountInfo.get(item.getItemId());
                }catch (Exception e) {
                    reservationTicketCount = 0;
                }
                remainingTicketCount = remainingTicketCount - reservationTicketCount;
            }
            if (remainingTicketCount < 0) {
                remainingTicketCount = 0;
            }
            ItemDto modifiedItem = new ItemDto(itemId, item.getItemName(), item.getTotalTicket(),
                    item.getPrice(), remainingTicketCount
            );
            modifiedItems.add(modifiedItem);
        }
        List<StoreImg> findImgs = store.getStoreImgList();
        List<String> modifiedStoreImgs = new ArrayList<>();
        for (StoreImg storeImg : findImgs) {
            String link = storeImg.getLink();
            if (storeImg.getIsThumbnail()) {
                modifiedStoreImgs.add(0, link);
            } else {
                modifiedStoreImgs.add(link);
            }
        }
        StoreResponseDto storeResponseDto = new StoreResponseDto();
        storeResponseDto.setStoreName(store.getStoreName());
        storeResponseDto.setCategory(store.getCategory());
        storeResponseDto.setBody(store.getBody());
        storeResponseDto.setLatitude(store.getLatitude());
        storeResponseDto.setLongitude(store.getLongitude());
        storeResponseDto.setKakao(store.getKakao());
        storeResponseDto.setContact(store.getContact());
        storeResponseDto.setAddress(store.getAddress());
        storeResponseDto.setCreatedAt(store.getCreatedAt());
        storeResponseDto.setItems(modifiedItems);
        storeResponseDto.setStoreImages(modifiedStoreImgs);
        try {
            storeResponseDto.setProfileImg(store.getMember().getProfileImg());
        } catch (NullPointerException e) {
            storeResponseDto.setProfileImg(null); // null 자리에 기본 이미지 넣을 수 있음
        }
        return storeResponseDto;
    }

}
