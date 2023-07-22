package actiOn.auth.filter;

import actiOn.auth.provider.TokenProvider;
import actiOn.auth.utils.MemberAuthorityUtil;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static actiOn.auth.utils.TokenPrefix.*;

/*
 클라이언트 측에서 전송된 request header에 포함된 JWT 검증을 위한 필터
 request 당 한 번 수행하므로 OncePerRequestFilter 이용

 검증이 끝나면 SecurityHolder를 이용해 SecurityContext에 인증된 Authentication을 저장
 */
@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final MemberAuthorityUtil authorityUtil;
    private final MemberService memberService;

    // 예외 로직 추가
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            // 액세스 토큰이 만료된 경우
            handleExpiredToken(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // 액세스 토큰 만료 시 리프레시 토큰을 검사하고 재발급
    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String refreshToken = extractRefreshTokenFromCookie(request);
            // 리프레시 토큰 검증
            Jws<Claims> claims = verifyRefreshToken(refreshToken);

            String email = claims.getBody().getSubject();
            Member member = memberService.findMemberByEmail(email);

            // 새로운 액세스 토큰 재발금
            String accessToken = tokenProvider.delegateAccessToken(member);
            response.setHeader(AUTHORIZATION.getType(), BEARER.getType() + accessToken);

            // 리프레시 토큰 쿠키에 저장
            response.setHeader("Set-Cookie", REFRESH.getType() + "=" + refreshToken +
                    "; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=3600;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // 리프레시 토큰도 만료된 경우
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("refresh token이 만료되었습니다. 다시 로그인해주세요.");
            response.getWriter().flush();
        }
    }

    private Jws<Claims> verifyRefreshToken(String refreshToken) throws Exception {
        // 토큰 검증 후 claims 반환
        String base64EncodedSecretKey = tokenProvider.encodedBase64SecretKey();
        Jws<Claims> claims = tokenProvider.getClaims(refreshToken, base64EncodedSecretKey);

        // 토큰 만료 검증
        if (tokenProvider.isExpired(claims)) {
            throw new Exception("리프레시 토큰이 만료되었습니다.");
        }

        return claims;
    }

    // refresh Token 추출
    private String extractRefreshTokenFromCookie(HttpServletRequest request) throws ServletException {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            System.out.println("쿠키가 없습니다.");
            throw new ServletException("쿠키가 없습니다.");
        }

        return Arrays.stream(cookies)
                .filter(cookie ->
                        cookie.getName().equals(REFRESH.getType()))
                .findFirst()
                .orElseThrow(() -> new ServletException("Refresh 토큰이 없습니다."))
                .getValue();
    }

    // JWT가 헤더에 포함되지 않거나, 헤더 값이 null인 경우 다음 필터로 건너뜀
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader(AUTHORIZATION.getType());

        return authorization == null || !authorization.startsWith(BEARER.getType());
    }

    // claims를 파싱하는 과정을 통해 JWT 검증
    private Map<String, Object> verifyJws(HttpServletRequest request) throws ServletException {
        String authorizationHeader = request.getHeader(AUTHORIZATION.getType());

        if (authorizationHeader.isEmpty()) {
            throw new ServletException("헤더가 비었습니다.");
        }

        String jws = authorizationHeader.replace(BEARER.getType(), "");
        String base64EncodedSecretKey = tokenProvider.encodedBase64SecretKey();

        Map<String, Object> claims = tokenProvider.getClaims(jws, base64EncodedSecretKey).getBody();
        return claims;
    }

    // Authentication 객체를 SecurityContext에 저장하기 위한 메서드
    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = authorityUtil.createAuthorities(roles);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
