package lk.ijse.project.layered.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private String customerId;
    private String name;
    private String address;
    private String contact;
    private String email;
}
