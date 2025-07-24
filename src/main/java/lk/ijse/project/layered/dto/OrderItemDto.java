package lk.ijse.project.layered.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemDto {
    private String itemId;
    private String orderId;
    private double qty;
    private double price;

}
