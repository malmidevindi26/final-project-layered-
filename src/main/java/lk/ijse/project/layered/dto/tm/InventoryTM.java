package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InventoryTM {
    private String inventoryId;
   // private String itemId;
    private String name;
    private String manufacturedDate;
    private String expaireDate;
    private String status;
    private double quantity;
    private double Price;
}
