package actiOn.auth.handler;

import actiOn.exception.util.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Authentication Exception이 발생할 때 호출되는 핸들러
@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception e = (Exception) request.getAttribute("exception");
        ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);

        logExceptionMessage(authException, e);
    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message;

        if (exception == null) {
            message = authException.getMessage();
        } else {
            message = exception.getMessage();
        }

        log.warn("# Unauthorized error happened: {}", message);
    }
}
