package actiOn.store.repository;

import actiOn.store.entity.QStore;
import actiOn.store.entity.Store;
import actiOn.wish.entity.QWish;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepositoryImpl implements StoreCustomRepository{

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void addLikeCount(Store selectStore) {
        QStore st = QStore.store;
        queryFactory.update(st)
                .set(st.likeCount, st.likeCount.add(1))
                .where(st.eq(selectStore))
                .execute();
    }

    @Override
    public void subLikeCount(Store selectStore) {
        QStore st = QStore.store;
        queryFactory.update(st)
                .set(st.likeCount, st.likeCount.subtract(1))
                .where(st.eq(selectStore))
                .execute();
    }
}
