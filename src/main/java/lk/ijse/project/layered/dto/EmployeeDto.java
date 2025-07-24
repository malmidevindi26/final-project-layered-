package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeDto {
    private String employeeId;
    private String name;
    private String address;
    private String contact;
    private double salary;
    private String hireDate;
    private String role;

}
