package actiOn.item.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 기존 아이템을 수정 후 저장
    public void updateItems(List<Item> findItems, List<Item> newItems) {
        for (int i = 0; i < findItems.size(); i++) {
            Item findItem = findItem(findItems.get(i));
            Item newItem = newItems.get(i);

            findItem.setItemName(newItem.getItemName());
            findItem.setPrice(newItem.getPrice());
            findItem.setTotalTicket(newItem.getTotalTicket());
            findItem.setStore(findItem.getStore());

            itemRepository.save(findItem);
        }
    }

    public Item findItem(Item item) {
        Optional<Item> findItem = itemRepository.findItemByItemId(item.getItemId());

        if (findItem.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND);
        }

        return findItem.get();
    }
}
