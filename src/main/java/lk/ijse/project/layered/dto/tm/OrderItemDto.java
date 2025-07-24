package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {
    private String itemId;
    private String orderId;
    private int quantity;
    private double price;
    private String date;
}
