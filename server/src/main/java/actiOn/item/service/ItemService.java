package actiOn.item.service;

import actiOn.item.entity.Item;
import actiOn.item.repository.ItemRepository;
import actiOn.store.entity.Store;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
    public List<Item> createItems(List<Item> items){
        List<Item> savedItems = new ArrayList<>();
        for (Item item : items) {
            savedItems.add(itemRepository.save(item));
        }
        return savedItems;
    }


}
