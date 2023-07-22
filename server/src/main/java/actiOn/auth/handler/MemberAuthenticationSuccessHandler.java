package actiOn.auth.handler;

import actiOn.auth.provider.TokenProvider;
import actiOn.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        // 액세스 토큰 저장 및 리프레시 토큰 쿠키 저장
        tokenProvider.generateTokenAndCookie(response, member);

        log.info("# Authenticated Successfully!");
    }
}
