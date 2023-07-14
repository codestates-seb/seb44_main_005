package actiOn.item.entity;

import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "TOTAL_TICKET", nullable = false)
    private Integer totalTicket;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public void validateTicketCount(int ticketCount){
        if (ticketCount > this.totalTicket){
            throw new IllegalArgumentException("티켓 수가 최대 허용량을 초과했습니다.");
        }
    }
}
