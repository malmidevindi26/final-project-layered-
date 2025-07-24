package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SupplierTM {
    private String supplierId;
    private String itemId;
    private String name;
    private String address;
    private String contact;
    private double amount;
    private String date;

}
