package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeEntity {
    private String id;
    private String name;
    private String address;
    private String phone;
    private double salary;
    private String date;
    private String role;


}
