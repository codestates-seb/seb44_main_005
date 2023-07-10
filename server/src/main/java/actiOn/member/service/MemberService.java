package actiOn.member.service;

import actiOn.Img.profileImg.ProfileImg;
import actiOn.Img.service.ImgService;
import actiOn.auth.utils.MemberAuthorityUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final MemberAuthorityUtil authorityUtil;
    private final ImgService imgService;

    // 회원 등록
    @Transactional(propagation = Propagation.REQUIRED)
    public Member createMember(Member member) {
        // 이메일, 닉네임, 휴대폰 번호 중복 검사
        verifyExistsEmail(member.getEmail());
        verifyExistsNickname(member.getNickname());
        verifyExistsPhoneNumber(member.getPhoneNumber());

        // Password 단방향 암호화
        String encryptedPW = encoder.encode(member.getPassword());
        member.setPassword(encryptedPW);

        // TODO DB에 User Role 저장
        List<String> roles = authorityUtil.createRoles(member.getEmail());
        member.setRoles(roles);

        // 프로필 기본 이미지 설정
        ProfileImg defaultImage = imgService.createDefaultProfileImg(member);
        member.setProfileImg(defaultImage);

        return memberRepository.save(member);
    }

    // 회원 정보 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateMember(Member member) {
        Member findMember = findMemberByEmail(member.getEmail());

        // 중복 확인 후 저장
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> {
                    verifyExistsNickname(nickname);
                    findMember.setNickname(nickname);
                });

        Optional.ofNullable(member.getPhoneNumber())
                .ifPresent(number -> {
                    verifyExistsPhoneNumber(number);
                    findMember.setPhoneNumber(number);
                });

        memberRepository.save(findMember);
    }

    // 이메일로 회원 조회
    public Member findMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        } else {
            return member.get();
        }
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


    public boolean isExistMember(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        return member.isPresent();
    }
}
