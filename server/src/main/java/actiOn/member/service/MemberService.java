package actiOn.member.service;

import actiOn.Img.service.ImgService;
import actiOn.auth.role.MemberRole;
import actiOn.auth.role.Role;
import actiOn.auth.role.RoleService;
import actiOn.business.entity.Business;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final ImgService imgService;
    private final RoleService roleService;

    // 회원 등록
    @Transactional(propagation = Propagation.REQUIRED)
    public Member createMember(Member member) {
        // 이메일, 닉네임, 휴대폰 번호 중복 검사
        verifyExistsEmail(member.getEmail());
        verifyExistsNickname(member.getNickname());
        if (member.getPhoneNumber() != null) {
            verifyExistsPhoneNumber(member.getPhoneNumber());
        }

        // Password 단방향 암호화
        String encryptedPW = encoder.encode(member.getPassword());
        member.setPassword(encryptedPW);

        // DB에 User Role 저장
        Role userRole = roleService.findUserRole();
        List<MemberRole> memberRoles = addedMemberRole(member, userRole);
        member.setMemberRoles(memberRoles);

        // 기본 프로필 이미지 등록
        String defaultImage = member.getDefaultImageLink();
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

    // 파트너 등록
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member registerPartnership(Business business, String email) {
        Member member = findMemberByEmail(email);

        member.setBusiness(business);

        // DB에 Partner Role 저장
        Role partnerRole = roleService.findPartnerRole();
        List<MemberRole> memberRoles = addedMemberRole(member, partnerRole);

        member.setMemberRoles(memberRoles);

        return memberRepository.save(member);
    }

    // 프로필 이미지 등록
    @Transactional(propagation = Propagation.REQUIRED)
    public void registerProfileImage(MultipartFile file, String email) throws IOException {
        Member member = findMemberByEmail(email);

        // 프로필 이미지 업로드
        String profileImg = imgService.uploadProfileImage(file, member);
        member.setProfileImg(profileImg);

        memberRepository.save(member);
    }

    // 구글 프로필 이미지 등록
    @Transactional(propagation = Propagation.REQUIRED)
    public void registerGoogleProfileImage(String profileImgUrl, String email) {
        Member member = findMemberByEmail(email);

        // 프로필 이미지 업로드
        member.setProfileImg(profileImgUrl);

        memberRepository.save(member);
    }

    // 프로필 이미지 삭제
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteProfileImage(String email) {
        Member member = findMemberByEmail(email);

        String defaultProfileImg = member.getDefaultImageLink();
        member.setProfileImg(defaultProfileImg);

        memberRepository.save(member);
    }

    // Role 저장
    public List<MemberRole> addedMemberRole(Member member, Role role) {
        MemberRole memberRole = new MemberRole();
        memberRole.setMember(member);
        memberRole.setRole(role);

        List<MemberRole> memberRoles = member.getMemberRoles();
        memberRoles.add(memberRole);

        return memberRoles;
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
