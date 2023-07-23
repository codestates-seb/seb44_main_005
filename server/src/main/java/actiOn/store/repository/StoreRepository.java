package actiOn.store.repository;

import actiOn.store.entity.Store;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAll(Sort sort);

    List<Store> findByCategory(String category, Sort sort);

    List<Store> findTop4ByOrderByLikeCountDesc();

    List<Store> findByStoreNameContainingOrderByRatingDesc(String keyword);
}
