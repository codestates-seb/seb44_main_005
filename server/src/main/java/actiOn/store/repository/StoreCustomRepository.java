package actiOn.store.repository;

import actiOn.store.entity.Store;

public interface StoreCustomRepository {

    void addLikeCount(Store store);

    void subLikeCount(Store store);

    void addReviewCount(Store store);

    void setRating(Store store, double avgRating);
}
