package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerEntity {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
