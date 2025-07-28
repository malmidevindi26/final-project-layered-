package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    private String itemId;
    private String orderId;
    private BigDecimal qty;
    private BigDecimal price;
}
