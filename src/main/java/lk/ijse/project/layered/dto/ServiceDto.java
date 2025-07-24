package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceDto {
    private String serviceId;
    private String name;
    private double price;
    private String description;
}
