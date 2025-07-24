package lk.ijse.project.layered.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    private String supplierId;
    private String itemId;
    private String name;
    private String address;
    private String contact;
    private double amount;
    private String date;
}
