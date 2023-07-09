package actiOn.auth.utils;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtil {
    // 로그인한 유저의 이메일 반환
    public static String getCurrentMemberEmail() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        // Authorization 헤더가 빠진 경우, 토큰 불일치 등 예외처리
        throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_PRINCIPAL);
    }
}
