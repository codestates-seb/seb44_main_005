package actiOn.member.mapper;

import actiOn.member.dto.MemberPatchDto;
import actiOn.member.dto.MemberPostDto;
import actiOn.member.dto.MemberResponseDto;
import actiOn.member.dto.PartnerResponseDto;
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

    default PartnerResponseDto partnerToPartnerStoreResponseDto(Member partner) {
        PartnerResponseDto.PartnerResponseDtoBuilder builder = PartnerResponseDto.builder();

        builder.stores(
                storesToPartnerStoreResponseDtos(partner.getStores())
        );

        return builder.build();
    }

    default List<PartnerResponseDto.PartnerStoreDto> storesToPartnerStoreResponseDtos(List<Store> stores) {

        List<PartnerResponseDto.PartnerStoreDto> partnerStoreDtos = new ArrayList<>();
        for (Store store : stores) {
            partnerStoreDtos.add(storeToPartnerStoreResponseDto(store));
        }

        return partnerStoreDtos;
    }

    PartnerResponseDto.PartnerStoreDto storeToPartnerStoreResponseDto(Store store);
}