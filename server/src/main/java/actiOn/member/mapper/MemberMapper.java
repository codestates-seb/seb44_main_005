package actiOn.member.mapper;

import actiOn.member.dto.MemberPatchDto;
import actiOn.member.dto.MemberPostDto;
import actiOn.member.entity.Member;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface MemberMapper {
    Member memberPostSignupDtoToMember(MemberPostDto.Signup requestBody);

    Member memberPatchDtoToMember(MemberPatchDto requestBody);
}
