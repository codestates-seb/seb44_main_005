package actiOn.store.service;

import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.map.response.GeoLocation;
import actiOn.map.service.KakaoMapService;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.store.dto.CategoryResponseDto;
import actiOn.store.dto.CategoryStoreDto;
import actiOn.store.dto.StoreResponseDto;
import actiOn.store.dto.mainrep.DataDto;
import actiOn.store.dto.mainrep.MainPageResponseDto;
import actiOn.store.dto.mainrep.RecommendDto;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import actiOn.wish.entity.Wish;
import actiOn.wish.service.WishService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final KakaoMapService kakaoMapService;
    private final MemberService memberService;
    private final WishService wishService;
    private final StoreImgRepository storeImgRepository;

    public Store createStore(Store store) { // store를 받아서, 주소를 가져온 다음, 그 주소를 카카오로 보내서 좌표를 받아옴
        GeoLocation location = kakaoMapService.addressToLocation(store.getAddress());
        store.setLatitude(Double.parseDouble(location.getLatitude()));
        store.setLongitude(Double.parseDouble(location.getLongitude()));
        List<Item> items = store.getItems();
        int lowPrice = 0;
        for (Item item : items) {
            item.setStore(store);
            int itemPrice = item.getPrice();
            if (lowPrice == 0 || itemPrice < lowPrice) lowPrice = itemPrice;
        }
        store.setLowPrice(lowPrice); // 0으로 나오는 문제 해결 필요
        return storeRepository.save(store);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        Store findStore = this.findStoreByStoreId(storeId);

        //Todo member가 올린 스토어인지 확인 필요
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

        if (!findStore.getMember().getMemberId().equals(findMember.getMemberId())) {
            throw new IllegalArgumentException("업체를 등록한 파트너만이 업체 삭제가 가능합니다.");
        }

        //Todo 업체를 삭제할 때 사업체 등록번호를 체크한다든지, 비밀번호를 받는 기능이 추가되면 어떨까?

        storeRepository.delete(findStore);

    }

    public Store findStoreByStoreId(long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
    }

    public List<Store> findStoreByCategory(String category, String sortFiled) {
        try {
            if (sortFiled.isEmpty()) {
                sortFiled = "likeCount";
            }
            if (category.equals("all") || category.isEmpty()) {
                if (sortFiled.equals("lowPrice")) {
                    return storeRepository.findAll(Sort.by(Sort.Direction.ASC, sortFiled));
                }
                if (sortFiled.equals("highPrice")) sortFiled = "lowPrice";
                return storeRepository.findAll(Sort.by(Sort.Direction.DESC, sortFiled));
            }
            if (sortFiled.equals("highPrice")) {
                return storeRepository.findByCategory(category, Sort.by(Sort.Direction.ASC, "lowPrice")); // 오름차순
            }

            return storeRepository.findByCategory(category, Sort.by(Sort.Direction.DESC, sortFiled)); // 내림차순
        } catch (Exception e) {
            return null;
        }

    }

    public List<Store> searchEnginOnStoreNameByKeyword(String keyword) {
        return storeRepository.findByStoreNameContainingOrderByRatingDesc(keyword);
    }

    //
    //메인페이지
    public MainPageResponseDto getMainPage() {

        MainPageResponseDto mainPageResponseDto = new MainPageResponseDto();

        List<RecommendDto> recommendDtos = new ArrayList<>();

        //Todo 좋아요 4개만 가져오기
        List<Store> wishTop4Stores = storeRepository.findTop4ByOrderByLikeCountDesc();

        for (Store store : wishTop4Stores) {

            String thumbnailImgLink = "";
            List<StoreImg> storeImgList = store.getStoreImgList();
            for (StoreImg storeImg : storeImgList) {
                if (storeImg.getIsThumbnail()) {
                    thumbnailImgLink = storeImg.getLink();
                }
            }

            RecommendDto recommendDto = new RecommendDto();
            recommendDto.setStoreId(store.getStoreId());
            recommendDto.setStoreName(store.getStoreName());
            recommendDto.setBody(store.getBody());
            recommendDto.setImg(thumbnailImgLink);
            recommendDtos.add(recommendDto);
        }

        //전체 업체 리스트 가져오기
        List<DataDto> dataDtos = new ArrayList<>();

        List<Store> getAllStores = storeRepository.findAll();
        for (Store store : getAllStores) {
            DataDto dataDto = new DataDto();
            dataDto.setStoreId(store.getStoreId());
            dataDto.setStoreName(store.getStoreName());
            dataDto.setLatitude(store.getLatitude());
            dataDto.setLongitude(store.getLongitude());
            dataDto.setCategory(store.getCategory());
            dataDtos.add(dataDto);
        }

        mainPageResponseDto.setRecommend(recommendDtos);
        mainPageResponseDto.setData(dataDtos);
        return mainPageResponseDto;
    }

    public List<Long> getWishStoreIdList(Member member) {
        List<Wish> wishList = wishService.getWishListByMember(member);
        List<Long> wishStoreIdList = new ArrayList<>();
        for (Wish wish : wishList) {
            wishStoreIdList.add(wish.getStore().getStoreId());
        }
        return wishStoreIdList;
    }

    public StoreResponseDto insertWishAtStoreResponseDto(Member member, StoreResponseDto storeResponseDto, long storeId) {
        List<Long> wishStoreIdList = getWishStoreIdList(member);
        if (wishStoreIdList.contains(storeId)) {
            storeResponseDto.setIsLike(true);
        }
        return storeResponseDto;
    }

    public CategoryResponseDto insertWishAtCategoryResponseDto(Member member, CategoryResponseDto categoryResponseDto) {
        List<Long> wishStoreIdList = getWishStoreIdList(member);
        List<CategoryStoreDto> categoryStoreDtoList = categoryResponseDto.getData();
        for (CategoryStoreDto store : categoryStoreDtoList) {
            long storeId = store.getStoreId();

            if (wishStoreIdList.contains(storeId)) {
                store.setIsLike(true);
            }
        }
        categoryResponseDto.setData(categoryStoreDtoList);
        return categoryResponseDto;
    }

    @Transactional
    public void deleteStoreImgByLinks(List<String> links) {
        for (String link : links) {
            storeImgRepository.deleteByLink(link.replace(" ", ""));
        }
    }
}