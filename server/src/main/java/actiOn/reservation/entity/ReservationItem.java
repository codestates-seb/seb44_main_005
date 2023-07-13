package actiOn.reservation.entity;


import actiOn.helper.audit.BaseEntity;
import actiOn.item.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ReservationItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationItemId;

    @Column(nullable = false)
    private int ticketCount;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
