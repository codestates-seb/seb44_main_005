package actiOn.auth.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    // 로그인한 유저의 이메일 반환
    public static String getCurrentMemberEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();
    }

}
