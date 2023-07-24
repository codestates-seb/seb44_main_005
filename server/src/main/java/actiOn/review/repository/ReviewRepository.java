package actiOn.review.repository;

import actiOn.member.entity.Member;
import actiOn.review.entity.Review;
import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStoreOrderByCreatedAtDesc(Store store);

    Long countByStoreAndMember(Store store, Member member);

}
