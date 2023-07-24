package actiOn.item.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
public class ItemDto {
    long itemId;

    String itemName;

    @Min(0)
    int totalTicket;

    @Min(0)
    int price;

    @Min(0)
    int remainingTicket;
}
