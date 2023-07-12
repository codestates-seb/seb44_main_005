package actiOn.store.repository;

import actiOn.store.entity.Store;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> , StoreCustomRepository{
//    List<Store> findByCategory(String category);

    List<Store> findAll(Sort sort);
    List<Store> findByCategory(String category, Sort sort);

    List<Store> findByStoreNameContainingOrderByRatingDesc(String keyword);//
}
