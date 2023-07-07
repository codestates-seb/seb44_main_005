package actiOn.member.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    // 회원 등록
    public Member createMember(Member member) {
        // 이메일, 닉네임, 휴대폰 번호 중복 검사
        verifyExistsEmail(member.getEmail());
        verifyExistsNickname(member.getNickname());
        verifyExistsPhoneNumber(member.getPhoneNumber());

        // Password 단방향 암호화
        String encryptedPW = encoder.encode(member.getPassword());
        member.setPassword(encryptedPW);

        // TODO DB에 User Role 저장

        return memberRepository.save(member);
    }

    // 이미 등록된 이메일인지 검증
    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    // 이미 등록된 휴대폰 번호인지 검증
    private void verifyExistsPhoneNumber(String phoneNumber) {
        Optional<Member> member = memberRepository.findByPhoneNumber(phoneNumber);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.PHONE_NUMBER_EXISTS);
        }
    }

    // 이미 등록된 닉네임인지 검증
    private void verifyExistsNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }
    }


}
