package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class InventoryEntity {
    private String id;
    private String name;
    private String manuDate;
    private String expiryDate;
    private String status;
    private double qty;
    private double price;
}
