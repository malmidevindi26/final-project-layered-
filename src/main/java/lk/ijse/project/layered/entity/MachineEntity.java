package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineEntity {
    private String id;
    private String type;
    private String status;
    private BigDecimal cost;
    private Date date;

}
