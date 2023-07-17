package actiOn.auth.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 사용자의 권한을 매핑, 생성하는 util
@Component
public class MemberAuthorityUtil {
    private final List<String> USER_ROLES_STRING = List.of("USER");
    private final List<String> PARTNER_ROLES_STRING = List.of("PARTNER", "USER");

    // DB에 저장된 Role을 기반으로 권한 정보 생성
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return authorities;
    }

    // DB 저장 용
    public List<String> getUserRoles() {
        return new ArrayList<>(USER_ROLES_STRING);
    }

    public List<String> getPartnerRoles() {
        return new ArrayList<>(PARTNER_ROLES_STRING);
    }
}
