package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTM {
    private String serviceId;
    private String name;
    private double price;
    private String description;
}
