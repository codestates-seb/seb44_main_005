package actiOn.item.repository;

import actiOn.item.entity.Item;
import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByItemId(Long itemId);

}
