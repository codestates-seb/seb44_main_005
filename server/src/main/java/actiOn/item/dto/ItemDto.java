package actiOn.item.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    String item;
    int totalTicket;
    int price;
    int remainingTicket;

    public ItemDto(String itemName, int maxCount, int price, int remainingTicket){
        this.item=itemName;
        this.totalTicket=maxCount;
        this.price=price;
        this.remainingTicket=remainingTicket;
    }
}
