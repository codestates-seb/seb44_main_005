package actiOn.auth.handler;

import actiOn.auth.provider.TokenProvider;
import actiOn.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static actiOn.auth.utils.TokenPrefix.*;

// 인증 성공 시 호출되는 핸들러
@Slf4j
@AllArgsConstructor
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    // 인증 성공 시 토큰 발급
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 인증에 성공하면 내부적으로 멤버 객체 할당됨
        Member member = (Member) authentication.getPrincipal();

        String accessToken = tokenProvider.delegateAccessToken(member);
        String refreshToken = tokenProvider.delegateRefreshToken(member);
        String loginResponse = tokenProvider.getLoginResponseJson(member);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 액세스 토큰 저장
        response.setHeader(AUTHORIZATION.getType(), BEARER.getType() + accessToken);
        response.setHeader("Access-Control-Allow-Origin", "http://ac-ti-on.s3-website.ap-northeast-2.amazonaws.com");

        // 리프레시 토큰 쿠키에 저장
        response.setHeader("Set-Cookie", REFRESH.getType() + "=" + refreshToken +
                "; Path=/; HttpOnly; Max-Age=3600;");

        response.getWriter().write(loginResponse);

        log.info("# Authenticated Successfully!");
    }
}
