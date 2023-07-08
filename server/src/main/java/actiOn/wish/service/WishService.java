package actiOn.wish.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public WishService(WishRepository wishRepository, StoreRepository storeRepository, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.storeRepository = storeRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void registerWish(Long storeId, Authentication authentication){
        //Todo Login 유저 찾기 -> 리팩토링. memberservice에서 findByEmail 메서드 사용 예정
        String loginUserEmail = authentication.getName();
        Member findMember = memberRepository.findByEmail(loginUserEmail).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //Todo Store 존재 여부 확인
        Store store = storeRepository.findById(storeId).orElseThrow(
                (() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND)));

        //Todo 이미 좋아요 되어 있으면 에러 반환 -> Frontend에서 요청 메서드 실수 있을수도 있기에 추가
        if (wishRepository.findByMemberAndStore(findMember,store).isPresent()){
            throw new BusinessLogicException(ExceptionCode.WISH_EXIST);
        }

        Wish wish = new Wish();
        wish.setMember(findMember);
        wish.setStore(store);
        wishRepository.save(wish);

        //Todo Store에 대한 찜 개수 +1 로직
//        storeRepository.updateLikeCount(store, true);
    }

    @Transactional
    public void deleteWish(Long storeId, Authentication authentication){
        //Todo Login 유저 찾기 -> 리팩토링. memberservice에서 findByEmail 메서드 사용 예정
        String loginUserEmail = authentication.getName();
        Member findMember = memberRepository.findByEmail(loginUserEmail).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //Todo Store 존재 여부 확인
        Store store = storeRepository.findById(storeId).orElseThrow(
                (() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND)));

        //Tdo Wish 존재 여부 확인
        Wish wish = wishRepository.findByMemberAndStore(findMember,store)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND));

        wishRepository.delete(wish);

        //Todo Store에 대한 찜 개수 -1 로직
//        storeRepository.updateLikeCount(store, true);
    }


}
