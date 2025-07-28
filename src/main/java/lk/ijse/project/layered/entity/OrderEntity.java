package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    private String orderId;
    private String customerId;
    private String category;
    private Date date;
    private String status;
    private String serviceId;
    private BigDecimal capacity;

}
