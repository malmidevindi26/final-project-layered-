package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {
    private String supplierId;
    private String itemId;
    private String name;
    private String address;
    private String contact;
    private BigDecimal amount;
    private Date date;
}
