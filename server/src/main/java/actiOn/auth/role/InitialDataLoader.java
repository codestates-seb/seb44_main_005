package actiOn.auth.role;

import actiOn.auth.utils.MemberAuthorityUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// 애플리케이션 실행 시 초기 데이터 생성
@Component
public class InitialDataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final MemberAuthorityUtil authorityUtil;

    public InitialDataLoader(RoleRepository roleRepository, MemberAuthorityUtil authorityUtil) {
        this.roleRepository = roleRepository;
        this.authorityUtil = authorityUtil;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // USER와 PARTNER를 DB에 저장
        List<String> userRoles = authorityUtil.getUserRoles();
        saveRoles(userRoles);

        List<String> partnerRoles = authorityUtil.getPartnerRoles();
        saveRoles(partnerRoles);
    }

    private void saveRoles(List<String> roles) {
        for (String role : roles) {
            // Role name이 중복되지 않는 것만 저장
            if (roleRepository.findByName(role).isEmpty()) {
                Role userRole = new Role(role);
                roleRepository.save(userRole);
            }
        }
    }
}
