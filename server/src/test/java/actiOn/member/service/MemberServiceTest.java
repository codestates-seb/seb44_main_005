package actiOn.member.service;

import actiOn.member.entity.Member;
import actiOn.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setEmail("test@example.com");
        member.setNickname("testNickname");
        member.setPhoneNumber("010-1234-1234");

        memberRepository.save(member);
    }

    @Test
    @DisplayName("멤버 정보 수정 - 닉네임 변경")
    void testUpdateMemberWithNickname() {
        // 수정된 멤버 생성
        Member updatedMember = new Member();
        updatedMember.setEmail("test@example.com");
        updatedMember.setNickname("newNickname");
        updatedMember.setPhoneNumber("010-1234-1234");

        // 가상의 Member를 반환하도록 설정
        when(memberRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(member));

        memberService.updateMember(updatedMember);

        assertEquals("newNickname", member.getNickname());
        assertEquals("010-1234-1234", member.getPhoneNumber());
    }

}
