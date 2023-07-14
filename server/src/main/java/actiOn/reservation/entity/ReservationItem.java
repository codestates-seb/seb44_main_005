package actiOn.reservation.entity;


import actiOn.helper.audit.BaseEntity;
import actiOn.item.entity.Item;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReservationItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationItemId;

    @Column(nullable = false)
    private int ticketCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
