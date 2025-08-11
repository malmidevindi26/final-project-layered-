package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyEntity {
    private String penaltyId;
    private String orderId;
    private String storeId;
    private double amount;
    private String reason;
}
