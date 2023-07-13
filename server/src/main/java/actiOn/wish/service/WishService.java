package actiOn.wish.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import actiOn.wish.entity.Wish;
import actiOn.wish.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final StoreRepository storeRepository;
    private final MemberService memberService;

    public WishService(WishRepository wishRepository, StoreRepository storeRepository, MemberService memberService) {
        this.wishRepository = wishRepository;
        this.storeRepository = storeRepository;
        this.memberService = memberService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void registerWish(Long storeId){
        //Todo Login 유저 찾기 -> 리팩토링. memberservice에서 findByEmail 메서드 사용 예정
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

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
        wishRepository.save(wish); //Wish가 insert 되는 순간 store 데이터에 s-Lock이 걸린다.

        //Todo Store에 대한 찜 개수 +1 로직
        storeRepository.addLikeCount(store); //store에서 update 쿼리 발생 시 Exclusive lock 발생
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteWish(Long storeId){
        //Todo Login 유저 찾기 -> 리팩토링. memberservice에서 findByEmail 메서드 사용 예정
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

        //Todo Store 존재 여부 확인
        Store store = storeRepository.findById(storeId).orElseThrow(
                (() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND)));

        //Tdo Wish 존재 여부 확인
        Wish wish = wishRepository.findByMemberAndStore(findMember,store)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND));

        wishRepository.delete(wish);

        //Todo Store에 대한 찜 개수 -1 로직
        storeRepository.subLikeCount(store);
    }//


    public List<Wish> getWishListByMember(Member member) {
        List<Wish> wishList = wishRepository.findByMember(member);
        return wishList;
    }


}
