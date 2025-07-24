package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class StoreManagementDto {
    private String storeId;
    private String orderId;
    private double capacity;
}
