package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTM {
    private String empId;
    private String name;
    private String address;
    private String contact;
    private double salary;
    private String hireDate;
    private String role;
}
