package actiOn.wish.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import actiOn.wish.entity.Wish;
import actiOn.wish.repository.WishRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final StoreRepository storeRepository;

    public WishService(WishRepository wishRepository, StoreRepository storeRepository) {
        this.wishRepository = wishRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public void registerWish(Long storeId, Authentication authentication){
        //Todo Login 유저 정보 찾는 로직 추가 필요
        String loginUserEmail = authentication.getName();

        //Todo Store 존재 여부 확인
        Store store = storeRepository.findById(storeId).orElseThrow(
                (() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND)));

        //Todo 이미 좋아요 되어 있으면 에러 반환
        if (wishRepository.findByMemberAndStore(findMember,store).isPresent()){
            throw new IllegalArgumentException("이미 좋아요 누르셨습니다.");
        }

        Wish wish = new Wish();
        //wish.setMember(member);
        wish.setStore(store);

        wishRepository.save(wish);

        //Todo Store에 대한 찜 개수 +1
        

    }
}
