package actiOn.item.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    long itemId;
    String item;
    int totalTicket;
    int price;
    int remainingTicket;

    public ItemDto(long itemId, String itemName, int maxCount, int price, int remainingTicket){
        this.itemId=itemId;
        this.item=itemName;
        this.totalTicket=maxCount;
        this.price=price;
        this.remainingTicket=remainingTicket;
    }
}
