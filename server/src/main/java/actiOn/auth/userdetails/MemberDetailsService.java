package actiOn.auth.userdetails;

import actiOn.auth.utils.MemberAuthorityUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

// DB에서 조회한 회원의 인증 정보를 기반으로 인증을 처리하는 클래스
@Component
@Service
@AllArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberAuthorityUtil authorityUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);

        Member findMember = optionalMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USERNAME_NOT_FOUND)
        );

        return new MemberDetails(findMember, authorityUtil);
    }
}
