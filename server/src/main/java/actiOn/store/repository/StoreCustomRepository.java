package actiOn.store.repository;

import actiOn.store.entity.Store;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreCustomRepository {

    void addLikeCount(Store store);

    void subLikeCount(Store store);

    void addReviewCount(Store store);
}
