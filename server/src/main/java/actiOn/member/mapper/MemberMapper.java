package actiOn.member.mapper;

import actiOn.Img.storeImg.StoreImg;
import actiOn.auth.dto.LoginResponseDto;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.dto.*;
import actiOn.member.entity.Member;
import actiOn.reservation.entity.Reservation;
import actiOn.store.entity.Store;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface MemberMapper {
    Member memberPostSignupDtoToMember(MemberPostDto.Signup requestBody);

    Member memberPatchDtoToMember(MemberPatchDto requestBody);

    default MemberResponseDto memberToMemberResponseDto(Member member) {
        return MemberResponseDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .profileImg(member.getProfileImg())
                .build();
    }

    // 파트너 정보 DTO
    default PartnerResponseDto partnerToPartnerResponseDto(Member partner) {
        PartnerResponseDto.PartnerResponseDtoBuilder builder = PartnerResponseDto.builder();

        builder.stores(
                storesToPartnerResponseDtos(partner.getStores())
        );

        return builder.build();
    }

    default List<PartnerResponseDto.StoreDto> storesToPartnerResponseDtos(List<Store> stores) {
        List<PartnerResponseDto.StoreDto> partnerDtos = new ArrayList<>();

        for (Store store : stores) {
            partnerDtos.add(storeToPartnerResponseDto(store));
        }

        return partnerDtos;
    }

    PartnerResponseDto.StoreDto storeToPartnerResponseDto(Store store);


    // 파트너 업체 DTO
    default PartnerStoreResponseDto partnerToPartnerStoreResponseDto(Member partner) {
        PartnerStoreResponseDto.PartnerStoreResponseDtoBuilder builder = PartnerStoreResponseDto.builder();

        builder.stores(
                storesToPartnerStoreResponseDtos(partner.getStores())
        );

        return builder.build();
    }

    default List<PartnerStoreResponseDto.PartnerStoreDto> storesToPartnerStoreResponseDtos(List<Store> stores) {
        List<PartnerStoreResponseDto.PartnerStoreDto> partnerStoreDtos = new ArrayList<>();

        for (Store store : stores) {
            partnerStoreDtos.add(storeToPartnerStoreResponseDto(store));
        }

        return partnerStoreDtos;
    }

    PartnerStoreResponseDto.PartnerStoreDto storeToPartnerStoreResponseDto(Store store);

    // 예약 내역 조회 DTO
    default MemberReservationResponseDto memberToMemberReservationsDto(Member member) {
        MemberReservationResponseDto.MemberReservationResponseDtoBuilder builder =
                MemberReservationResponseDto.builder();

        builder.data(
                reservationsToMemberReservationResponseDtos(member.getReservations())
        );

        return builder.build();
    }

    default List<MemberReservationResponseDto.MemberReservationDto> reservationsToMemberReservationResponseDtos(
            List<Reservation> reservations) {

        List<MemberReservationResponseDto.MemberReservationDto> memberReservationDtos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            memberReservationDtos.add(reservationToMemberReservationResponseDto(reservation));
        }

        return memberReservationDtos;
    }

    default MemberReservationResponseDto.MemberReservationDto reservationToMemberReservationResponseDto(Reservation reservation) {
        MemberReservationResponseDto.MemberReservationDto
                .MemberReservationDtoBuilder builder =
                MemberReservationResponseDto.MemberReservationDto.builder();

        Store store = reservation.getStore();
        List<StoreImg> storeImgList = reservation.getStore().getStoreImgList();

        // store 사진 null인 경우 예외처리
        if (storeImgList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.NULL_STORE_IMAGE);
        }

        builder.storeId(store.getStoreId());
        builder.kakao(store.getKakao());

        String thumbnailLink = findThumbnailImage(storeImgList).getLink();
        builder.storeImg(thumbnailLink);
        builder.storeName(store.getStoreName());
        builder.reservationDate(reservation.getReservationDate());
        builder.totalPrice(reservation.getTotalPrice());
        builder.reservationId(reservation.getReservationId());
        builder.itemCount(reservation.getReservationItems().size());
        builder.reservationStatus(reservation.getReservationStatus().getStepDescription());

        return builder.build();
    }

    // 썸네일 사진 탐색
    default StoreImg findThumbnailImage(List<StoreImg> storeImgList) {
        return storeImgList.stream()
                .filter(StoreImg::getIsThumbnail)
                .findAny()
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.THUMBNAIL_NOT_FOUND));
    }

    // 구글 로그인 이후 response
    default LoginResponseDto memberGoogleLoginResponseDto(Member member) {
        return LoginResponseDto.builder()
                .role(member.getRoleName())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImg())
                .build();
    }
}