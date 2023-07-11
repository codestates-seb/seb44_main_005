package actiOn.wish.repository;

import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import actiOn.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndStore(Member member, Store store);
}
