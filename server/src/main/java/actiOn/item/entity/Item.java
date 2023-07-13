package actiOn.item.entity;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
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

    @Column(name = "MAX_COUNT", nullable = false)
    private Integer maxCount;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public void validateTicketCount(int ticketCount){
        if (ticketCount > this.maxCount){
            throw new BusinessLogicException(ExceptionCode.TICKET_OVER);
        }
    }
}
