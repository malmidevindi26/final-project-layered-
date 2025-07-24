package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentTM {
    private String paymentId;
    private String orderId;
    private double promotionAmount;
    private double penaltyAmount;
    private double amount;
    private String method;
    private String status;
    private String date;

}
