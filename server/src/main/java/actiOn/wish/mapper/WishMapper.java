package actiOn.wish.mapper;

import actiOn.Img.storeImg.StoreImg;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import actiOn.wish.dto.WishlistResponseDto;
import actiOn.wish.entity.Wish;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WishMapper {
    // 특정 회원의 위시리스트 response DTO
    default WishlistResponseDto memberToWishlistResponseDto(Member member) {
        WishlistResponseDto.WishlistResponseDtoBuilder builder = WishlistResponseDto.builder();

        builder.stores(
                wishlistToWishlistResponseDtos(member.getWishList())
        );

        return builder.build();
    }

    default List<WishlistResponseDto.WishStoreDto> wishlistToWishlistResponseDtos(List<Wish> wishList) {
        List<WishlistResponseDto.WishStoreDto> wishlistDtos = new ArrayList<>();

        for (Wish wish : wishList) {
            wishlistDtos.add(wishToWishlistResponseDto(wish));
        }

        return wishlistDtos;
    }

    default WishlistResponseDto.WishStoreDto wishToWishlistResponseDto(Wish wish) {
        WishlistResponseDto.WishStoreDto.WishStoreDtoBuilder builder =
                WishlistResponseDto.WishStoreDto.builder();

        Store wishStore = wish.getStore();
        List<StoreImg> storeImgList = wishStore.getStoreImgList();

        // store 사진 null인 경우 예외처리
        if (storeImgList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.NULL_STORE_IMAGE);
        }

        builder.storeId(wishStore.getStoreId());
        builder.category(wishStore.getCategory());
        builder.title(wishStore.getStoreName());
        builder.content(wishStore.getBody());
        builder.address(wishStore.getAddress());
        builder.rating(wishStore.getRating());
        builder.reviewCount(wishStore.getReviewCount());
        builder.price(wishStore.getLowPrice());

        builder.isLike(true); // TODO true만 가능?

        String thumbnailLink = findThumbnailImage(storeImgList).getLink();
        builder.img(thumbnailLink);

        return builder.build();
    }

    // 썸네일 사진 탐색
    default StoreImg findThumbnailImage(List<StoreImg> storeImgList) {
        return storeImgList.stream()
                .filter(StoreImg::getIsThumbnail)
                .findAny()
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.THUMBNAIL_NOT_FOUND));
    }
}
