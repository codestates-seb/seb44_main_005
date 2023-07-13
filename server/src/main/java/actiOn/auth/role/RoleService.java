package actiOn.auth.role;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    private static final String USER = "USER";
    private static final String PARTNER = "PARTNER";

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findUserRole() {
        Optional<Role> role = roleRepository.findByName(USER);

        if (role.isPresent()) {
            return role.get();
        }

        throw new BusinessLogicException(ExceptionCode.ROLE_NOT_FOUND);
    }

    public Role findPartnerRole() {
        Optional<Role> role = roleRepository.findByName(PARTNER);

        if (role.isPresent()) {
            return role.get();
        }

        throw new BusinessLogicException(ExceptionCode.ROLE_NOT_FOUND);
    }
}
