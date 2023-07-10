package actiOn.item.entity;

import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

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

    @Column(name = "MAX_COUNT", nullable = false)
    private Integer maxCount;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;
}
