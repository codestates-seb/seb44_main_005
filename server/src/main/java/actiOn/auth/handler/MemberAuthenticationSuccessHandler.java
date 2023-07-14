package actiOn.auth.handler;

import actiOn.auth.dto.LoginResponseDto;
import actiOn.auth.provider.TokenProvider;
import actiOn.helper.util.JsonUtil;
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
        String loginResponse = getLoginResponseJson(member);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(AUTHORIZATION.getType(), BEARER.getType() + accessToken);
        response.setHeader(REFRESH.getType(), refreshToken);
        response.getWriter().write(loginResponse);

        log.info("# Authenticated Successfully!");
    }

    // 로그인 response를 Json 형식으로 반환
    private String getLoginResponseJson(Member member) {
        String role = member.getRoleName();
        String nickname = member.getNickname();
        String profileImage = member.getProfileImgLink();

        LoginResponseDto responseDto = new LoginResponseDto(role, nickname, profileImage);
        return JsonUtil.toJson(responseDto, LoginResponseDto.class);
    }
}
