package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PenaltyTm {
    private String penaltyId;
    private String orderId;
    private String storeId;
    private double amount;
    private String reason;
}
