package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private String paymentId;
    private String orderId;
    private BigDecimal promotionAmount;
    private BigDecimal penaltyAmount;
    private BigDecimal totalAmount;
    private String method;
    private String status;
    private Date date;
}
