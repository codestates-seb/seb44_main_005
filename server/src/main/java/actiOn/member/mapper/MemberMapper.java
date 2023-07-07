package actiOn.member.mapper;

import actiOn.member.dto.MemberPostDto;
import actiOn.member.entity.Member;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface MemberMapper {
    Member memberPostSignupToMember(MemberPostDto.Signup requestBody);
}
