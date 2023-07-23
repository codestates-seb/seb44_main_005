package actiOn.auth.oauth2;

import actiOn.auth.provider.TokenProvider;
import actiOn.auth.role.MemberRole;
import actiOn.auth.role.Role;
import actiOn.auth.role.RoleService;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

import static actiOn.auth.utils.TokenPrefix.*;

// OAuth2 인증에 성공하면 호출되는 핸들러
@Slf4j
@AllArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberService memberService;
    private final RoleService roleService;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String profileImgUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));

        Member member;

        // 기존 회원인지 확인
        if (memberService.isExistMember(email)) {
            member = memberService.findMemberByEmail(email);
        } else { // 신규 회원인 경우 회원 생성 및 저장
            member = createNewMember(email);
            memberService.createMember(member);
            // 프로필 이미지 등록
            memberService.registerGoogleProfileImage(profileImgUrl, email);
        }

        redirect(request, response, member);
    }

    // 프론트로 JWT 전송하기 위해 redirect하는 메서드
    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {

        String accessToken = tokenProvider.delegateAccessToken(member);
        String refreshToken = tokenProvider.delegateRefreshToken(member);
        String loginResponse = tokenProvider.getLoginResponseJson(member);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 액세스 토큰 저장
        response.setHeader(AUTHORIZATION.getType(), BEARER.getType() + accessToken);

        // 리프레시 토큰 쿠키에 저장
        response.setHeader("Set-Cookie", REFRESH.getType() + "=" + refreshToken +
                "; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=3600;");

        response.getWriter().write(loginResponse);

        String uri = createURI(accessToken, member);

        log.info("# Google Authenticated Successfully!");
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private Member createNewMember(String email) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(generateRandomPassword());
        member.setNickname(generateNicknameFromEmail(email));

        // USER 권한 부여
        Role userRole = roleService.findUserRole();
        List<MemberRole> memberRole = memberService.addedMemberRole(member, userRole);
        member.setMemberRoles(memberRole);

        return member;
    }

    // 임의의 비밀번호 생성
    private String generateRandomPassword() {
        int length = 10;
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

        SecureRandom random = new SecureRandom();
        String password = random
                .ints(length, 0, charset.length())
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        return password;
    }

    // 이메일을 기반으로 닉네임 생성
    private String generateNicknameFromEmail(String email) {
        String nickname = email.split("@")[0];
        return nickname;
    }

    private String createURI(String accessToken, Member member) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("nickname", member.getNickname());

        // 컨트롤러로 보낸 후 프론트로 리다이렉트 시도
        return UriComponentsBuilder.newInstance()
                // 프론트 도메인
                .scheme("http")
                .host("ac-ti-on.s3-website.ap-northeast-2.amazonaws.com") // s3 엔드포인트
//                .port(5173)
                .path("/oauth2/authorization/google/success")
                .queryParams(queryParams)
                .build().toUri()
                .toString();
    }
}
