package actiOn.reservation.repository;

import actiOn.reservation.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem,Long> {
}
