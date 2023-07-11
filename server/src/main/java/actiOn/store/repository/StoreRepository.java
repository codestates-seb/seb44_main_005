package actiOn.store.repository;

import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> , StoreCustomRepository{
    List<Store> findByCategory(String category);

    List<Store> findByCategoryOrderByRatingDesc(String category); // 평점 순 정렬 //null 넣으면 모든 store 가져옴

    List<Store> findByCategoryOrderByReviewCountDesc(String category); //리뷰 많은 순 정렬

    List<Store> findByCategoryOrderByLowPriceAsc(String category); //낮은 가격 순 정렬

    List<Store> findByCategoryOrderByLikeCount(String category);


}
