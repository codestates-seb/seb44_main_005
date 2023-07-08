package actiOn.auth.filter;

import actiOn.auth.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 클라이언트의 로그인 인증을 처리하는 필터 (엔트리포인트)
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // 사용자의 로그인 요청을 받고, 인증을 처리하는 로직
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // DTO 클래스로 역직렬화
        ObjectMapper mapper = new ObjectMapper();
        LoginDto loginDto = mapper.readValue(request.getInputStream(), LoginDto.class);

        // 사용자 인증을 시도하기 위한 토큰 생성(아직 인증이 되진 않은 상태)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getPassword(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 인증 성공 핸들러(MemberAuthenticationSuccessHandler 호출)
        this.getSuccessHandler()
                .onAuthenticationSuccess(request, response, authResult);
    }
}
