package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeEntity {
    private String id;
    private String name;
    private String address;
    private String phone;
    private BigDecimal salary;
    private Date date;
    private String role;


}
