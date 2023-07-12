package actiOn.member.mapper;

import actiOn.member.dto.*;
import actiOn.member.entity.Member;
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
                .profileImg(member.getProfileImg().getLink())
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

    default List<PartnerResponseDto.PartnerStoreDto> storesToPartnerResponseDtos(List<Store> stores) {
        List<PartnerResponseDto.PartnerStoreDto> partnerDtos = new ArrayList<>();

        for (Store store : stores) {
            partnerDtos.add(storeToPartnerResponseDto(store));
        }

        return partnerDtos;
    }

    PartnerResponseDto.PartnerStoreDto storeToPartnerResponseDto(Store store);


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

}