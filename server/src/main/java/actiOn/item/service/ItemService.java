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
            List<ItemDto> itemDtos = new ArrayList<>();
            Store findStore = storeService.findStoreByStoreId(storeId);
            List<Item> findItems = findStore.getItems();
            Map<Long,Integer> reservationTickets = reservationService.reservationTicketCount(findStore,date);
            for (Item item : findItems) {
                System.out.println("maxcount");
                System.out.println(item.getMaxCount());
                System.out.println("reservation");
                System.out.println(reservationTickets.get(item.getItemId()));
                int remainingTicketCount = item.getMaxCount()-reservationTickets.get(item.getItemId());
                if (remainingTicketCount <0) remainingTicketCount=0;
                ItemDto itemDto = new ItemDto(
                        item.getItemName(),
                        item.getMaxCount(),
                        item.getPrice(),
                        remainingTicketCount
                );
                itemDtos.add(itemDto);
            }
            return itemDtos;
        }catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


}
