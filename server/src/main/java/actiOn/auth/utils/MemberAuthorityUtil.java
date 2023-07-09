package actiOn.auth.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// 사용자의 권한을 매핑, 생성하는 util
@Component
public class MemberAuthorityUtil {
    private final List<GrantedAuthority> PARTNER_ROLES =
            AuthorityUtils.createAuthorityList("ROLE_PARTNER", "ROLE_USER");
    private final List<GrantedAuthority> USER_ROLES =
            AuthorityUtils.createAuthorityList("ROLE_USER");

    private final List<String> PARTNER_ROLES_STRING = List.of("PARTNER", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");

    // TODO 메모리 상의 ROLE을 기반으로 권한 정보 생성
    public List<GrantedAuthority> createAuthorities(String email) {
        return USER_ROLES;
    }

    // DB에 저장된 Role을 기반으로 권한 정보 생성
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities =
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

        return authorities;
    }

    // TODO DB 저장 용
    public List<String> createRoles() {
        return USER_ROLES_STRING;
    }
}
