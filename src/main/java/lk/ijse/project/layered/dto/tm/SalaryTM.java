package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalaryTM {
    private String salaryId;
    private String empId;
    private double amount;
    private String date;
}
