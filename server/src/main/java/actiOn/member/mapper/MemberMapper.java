package actiOn.member.mapper;

import actiOn.member.dto.MemberPatchDto;
import actiOn.member.dto.MemberPostDto;
import actiOn.member.dto.MemberResponseDto;
import actiOn.member.entity.Member;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

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
}