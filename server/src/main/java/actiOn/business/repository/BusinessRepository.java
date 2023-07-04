package actiOn.business.repository;

import actiOn.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Long, Business> {
}
