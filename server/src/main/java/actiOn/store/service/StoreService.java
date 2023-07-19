package actiOn.store.service;

import actiOn.Img.service.ImgService;
import actiOn.Img.storeImg.StoreImg;
import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.item.service.ItemService;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final KakaoMapService kakaoMapService;
    private final MemberService memberService;
    private final WishService wishService;
    private final ItemService itemService;
    private final ImgService imgService;

    private Store shapingStore(Store store) {
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
        return store;
    }

    public Store createStore(Store store) { // store를 받아서, 주소를 가져온 다음, 그 주소를 카카오로 보내서 좌표를 받아옴
        Store shapedStore = shapingStore(store);
        Member storeCreator = memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail());
        shapedStore.setMember(storeCreator);
        return storeRepository.save(shapedStore);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Store updateStore(Store store, long storeId) {
        Store findStore = findverifyIdentityStore(storeId);
        List<Item> findItems = findStore.getItems();
        List<Item> newItems = store.getItems();

        // 기존 아이템 정보 업데이트
        List<Item> updateItems = itemService.updateItems(findItems, newItems);
        findStore.setItems(updateItems);

        shapingFindStore(findStore, store);

        int lowPrice = computeLowPrice(newItems);
        findStore.setLowPrice(lowPrice); // 0으로 나오는 문제 해결 필요

        findStore.setStoreName(store.getStoreName());
        findStore.setCategory(store.getCategory());
        findStore.setBody(store.getBody());
        findStore.setKakao(store.getKakao());
        findStore.setContact(store.getContact());

        return storeRepository.save(findStore);
    }

    private int computeLowPrice(List<Item> items) {
        int lowPrice = 0;

        for (Item item : items) {
            int itemPrice = item.getPrice();
            if (lowPrice == 0 || itemPrice < lowPrice) lowPrice = itemPrice;
        }

        return lowPrice;
    }

    // 위도, 경도, 주소 설정
    private void shapingFindStore(Store findStore, Store store) {
        GeoLocation location = kakaoMapService.addressToLocation(store.getAddress());
        findStore.setAddress(store.getAddress());
        findStore.setLatitude(Double.parseDouble(location.getLatitude()));
        findStore.setLongitude(Double.parseDouble(location.getLongitude()));
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

    public List<Long> getWishStoreIdList(Member member) {
        List<Wish> wishList = wishService.getWishListByMember(member);
        List<Long> wishStoreIdList = new ArrayList<>();
        for (Wish wish : wishList) {
            long storeId = wish.getStore().getStoreId();
            wishStoreIdList.add(storeId);
        }
        return wishStoreIdList;
    }

    public Store findStoreByStoreId(long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
    }

    private void isValidValue(String category, String sortField) {
        List<String> categoryList = Arrays.asList("all", "스노클링/다이빙", "수상레저", "서핑", "승마", "ATV");
        List<String> sortFieldList = Arrays.asList("likeCount", "rating", "lowPrice", "highPrice", "reviewCount");

        if (!categoryList.contains(category) || !sortFieldList.contains(sortField)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER_VALUE);
        }
    }

    public List<Store> findStoreByCategory(String category, String sortFiled) {
        isValidValue(category, sortFiled);
        Sort.Direction sortOption = Sort.Direction.DESC;
        if (sortFiled.equals("lowPrice")) sortOption = Sort.Direction.ASC;
        if (sortFiled.equals("highPrice")) sortFiled = "lowPrice";
        if (category.equals("all")) return storeRepository.findAll(Sort.by(sortOption, sortFiled));
        return storeRepository.findByCategory(category, Sort.by(sortOption, sortFiled));
    }

    public List<Store> searchEnginOnStoreNameByKeyword(String keyword) {
        return storeRepository.findByStoreNameContainingOrderByRatingDesc(keyword);
    }

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

    public StoreResponseDto insertWishAtStoreResponseDto(StoreResponseDto responseDto, long storeId) {
        if (AuthUtil.getCurrentMemberEmail().equals("anonymousUser")) return responseDto;
        List<Long> wishStoreIdList =
                getWishStoreIdList(memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail()));
        if (wishStoreIdList.contains(storeId)) {
            responseDto.setIsLike(true);
        }
        return responseDto;

    }

    public CategoryResponseDto insertWishAtCategoryResponseDto(CategoryResponseDto categoryResponseDto) {
        if (AuthUtil.getCurrentMemberEmail().equals("anonymousUser")) return categoryResponseDto;
        List<Long> wishStoreIdList =
                getWishStoreIdList(memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail()));
        List<CategoryStoreDto> categoryStoreDtoList = categoryResponseDto.getData();
        for (CategoryStoreDto store : categoryStoreDtoList) {
            if (wishStoreIdList.contains(store.getStoreId())) store.setIsLike(true);
        }
        categoryResponseDto.setData(categoryStoreDtoList);
        return categoryResponseDto;
    }

    @Transactional
    public void deleteStoreImgLink(String link, long storeId, Boolean doVerify) {
        findverifyIdentityStore(storeId); // TODO doVerify 삭제

        imgService.deleteStoreImage(link);
    }

    public Store findverifyIdentityStore(long storeId) {
        String memberEmail = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(memberEmail);

        Optional<Store> store = storeRepository.findById(storeId);

        if (store.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND);
        }

        if (!store.get().getMember().equals(member)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER);
        }

        return store.get();
    }

    public void storeImageUpload(List<MultipartFile> images, long storeId, MultipartFile thumbnailImage) throws IOException {
        findverifyIdentityStore(storeId); // 본인 검증
        Store findStore = findStoreByStoreId(storeId); // 스토어 찾기
        if (images == null) images = new ArrayList<>();
        images.add(0, null);
        if (thumbnailImage != null) images.add(0, thumbnailImage);

        imgService.uploadStoreImage(images, findStore, thumbnailImage); // 업로드 //
    }

    public void validateImagePost(List<MultipartFile> images, MultipartFile thumbnailImage) {
        System.out.println(thumbnailImage);
        if (images == null) throw new BusinessLogicException(ExceptionCode.IMAGE_LIST_IS_EMPTY);
        if (thumbnailImage == null) throw new BusinessLogicException(ExceptionCode.THUMBNAIL_IS_NULL);
    }
}