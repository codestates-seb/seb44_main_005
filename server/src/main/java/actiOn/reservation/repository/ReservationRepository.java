package actiOn.reservation.repository;

import actiOn.reservation.entity.Reservation;
import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReservationDateAndStore(LocalDate reservationDate, Store store);

}
