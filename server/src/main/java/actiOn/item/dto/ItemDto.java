package actiOn.item.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemDto {
    long itemId;
    String itemName;
    int totalTicket;
    int price;
    int remainingTicket;
}
