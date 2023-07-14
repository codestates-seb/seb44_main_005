package actiOn.reservation.repository;

import actiOn.item.entity.Item;
import actiOn.reservation.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationItemRepository extends JpaRepository<ReservationItem,Long> {

    @Query("SELECT SUM(ri.ticketCount) from ReservationItem ri WHERE ri.item = :item")
    Integer getTotalTicketCountByItem(Item item);

}