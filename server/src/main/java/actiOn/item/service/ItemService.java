package actiOn.item.service;

import actiOn.item.dto.ItemDto;
import actiOn.item.entity.Item;
import actiOn.item.repository.ItemRepository;
import actiOn.reservation.service.ReservationService;
import actiOn.store.entity.Store;
import actiOn.store.service.StoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final StoreService storeService;
    private final ReservationService  reservationService;

    public ItemService(StoreService storeService, ReservationService reservationService) {
        this.storeService = storeService;
        this.reservationService = reservationService;
    }

    public List<ItemDto> findItemsByStoreIdAndDate(long storeId, LocalDate date) {
        try{
            //Todo 예외처리하기
            List<ItemDto> itemDtos = new ArrayList<>();
            Store findStore = storeService.findStoreByStoreId(storeId);
            List<Item> findItems = findStore.getItems();
            Map<Long,Integer> reservationTickets = reservationService.reservationTicketCount(findStore,date);
            for (Item item : findItems) {
                int reservationTicketCount = // 예약된 티켓이 없다면 null로 나오므로, null과 int는 연산이 불가능하므로, int로 변환
                        reservationTickets.containsKey(item.getItemId())
                                ? reservationTickets.get(item.getItemId()) : 0;

                int remainingTicketCount = item.getMaxCount()-reservationTicketCount;
                if (remainingTicketCount <0) remainingTicketCount=0;
                ItemDto itemDto = new ItemDto(
                        item.getItemId(),
                        item.getItemName(),
                        item.getMaxCount(),
                        item.getPrice(),
                        remainingTicketCount
                );
                itemDtos.add(itemDto);
            }
            return itemDtos;
        }catch (Exception e) {
            return null;
        }
    }


}
