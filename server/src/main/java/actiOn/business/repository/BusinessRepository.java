package actiOn.business.repository;

import actiOn.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    @Query("SELECT b FROM Business b WHERE b.registrationNumber = :registrationNumber")
    Optional<Business> findBusinessByRegistrationNumber(String registrationNumber);

}
