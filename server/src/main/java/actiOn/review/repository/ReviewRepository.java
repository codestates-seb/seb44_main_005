package actiOn.review.repository;

import actiOn.review.entity.Review;
import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> , ReviewCustomRepository{

    List<Review> findAllByStore(Store store);

//    @Query(value = "SELECT count(r) FROM Review r WHERE r.store.storeId = ?1")
//    long countByStore(Store store);

}
