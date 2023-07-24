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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "TOTAL_TICKET", nullable = false)
    private Integer totalTicket;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.SAVED;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public enum ItemStatus {
        SAVED, DELETED
    }
}
