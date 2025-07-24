package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PenaltyDto {
    private String penaltyId;
    private String orderId;
    private String storeId;
    private double amount;
    private String reason;

    public PenaltyDto(String orderId, String storeId, double amount, String date) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.amount = amount;
        this.reason = date;

    }
}
