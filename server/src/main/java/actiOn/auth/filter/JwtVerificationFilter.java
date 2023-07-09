package actiOn.auth.filter;

import actiOn.auth.provider.TokenProvider;
import actiOn.auth.utils.MemberAuthorityUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static actiOn.auth.utils.TokenPrefix.AUTHORIZATION;
import static actiOn.auth.utils.TokenPrefix.BEARER;

/*
 클라이언트 측에서 전송된 request header에 포함된 JWT 검증을 위한 필터
 request 당 한 번 수행하므로 OncePerRequestFilter 이용

 검증이 끝나면 SecurityHolder를 이용해 SecurityContext에 인증된 Authentication을 저장
 */
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final MemberAuthorityUtil authorityUtil;

    public JwtVerificationFilter(TokenProvider tokenProvider, MemberAuthorityUtil authorityUtil) {
        this.tokenProvider = tokenProvider;
        this.authorityUtil = authorityUtil;
    }

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
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // JWT가 헤더에 포함되지 않거나, 헤더 값이 null인 경우 다음 필터로 건너뜀
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader(AUTHORIZATION.getType());

        return authorization == null || !authorization.startsWith(BEARER.getType());
    }

    // claims를 파싱하는 과정을 통해 JWT 검증
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader(AUTHORIZATION.getType()).replace(BEARER.getType(), "");
        String base64EncodedSecretKey = tokenProvider.encodedBase64SecretKey(tokenProvider.getSecretKey());

        Map<String, Object> claims = tokenProvider.getClaims(jws, base64EncodedSecretKey).getBody();
        return claims;
    }

    // Authentication 객체를 SecurityContext에 저장하기 위한 메서드
    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtil.createAuthorities((String) claims.get("roles"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
