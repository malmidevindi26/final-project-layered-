package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryEntity {
    private String salaryId;
    private String employeeId;
    private BigDecimal salary;
    private Date issuedDate;
}
