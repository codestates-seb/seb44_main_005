package actiOn.review.repository;

import actiOn.review.entity.QReview;
import actiOn.store.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class ReviewRepositoryImpl implements ReviewCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Double avgStoreRating(Store store) {
        QReview review = QReview.review;
        Integer sumRating = queryFactory
                .select(review.rating.sum())
                .from(review)
                .where(review.store.eq(store))
                .fetchOne();

        Long reviewCount = queryFactory
                .select(review.count())
                .from(review)
                .where(review.store.eq(store))
                .fetchOne();

        if (sumRating == null || reviewCount == null || reviewCount == 0){
            return 0.0;
        }

        return (double) sumRating / reviewCount;
    }
}
