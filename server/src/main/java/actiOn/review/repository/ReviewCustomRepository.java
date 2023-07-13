package actiOn.review.repository;


import actiOn.store.entity.Store;

public interface ReviewCustomRepository {

    Double avgStoreRating(Store store);
}
