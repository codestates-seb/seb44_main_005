package actiOn.wish.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import actiOn.wish.entity.Wish;
import actiOn.wish.repository.WishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishService {
    private final WishRepository wishRepository;

    public List<Wish> getWishListByMember(Member member) {
        List<Wish> wishList = wishRepository.findByMember(member);
        return wishList;
    }

    public void isNotExistWish(Member member, Store store) {
        Optional<Wish> wish = wishRepository.findByMemberAndStore(member, store);

        if (wish.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.WISH_EXIST);
        }
    }

    public Wish isExistWish(Member member, Store store) {
        Optional<Wish> wish = wishRepository.findByMemberAndStore(member, store);

        if (wish.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND);
        }
        return wish.get();
    }

    public Wish generateWish(Member member, Store store) {
        Wish wish = new Wish();
        wish.setMember(member);
        wish.setStore(store);

        return wishRepository.save(wish); //Wish가 insert 되는 순간 store 데이터에 s-Lock이 걸린다.
    }

    public void deleteWish(Wish wish) {
        wishRepository.delete(wish);
    }
}
