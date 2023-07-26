package actiOn.reservation.repository;

import actiOn.member.entity.Member;
import actiOn.reservation.entity.Reservation;
import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReservationDateAndStore(LocalDate reservationDate, Store store);

    Optional<Reservation> findReservationByMemberAndStore(Member member, Store store);

    Optional<Reservation> findReservationByMemberAndStoreAndReservationStatus(
            Member member, Store store, Reservation.ReservationStatus reservationStatus);

    int countByStoreAndMemberAndReservationStatus(Store store, Member member, Reservation.ReservationStatus reservationStatus);
}
