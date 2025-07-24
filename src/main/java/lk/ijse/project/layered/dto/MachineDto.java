package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MachineDto {
    private String machineId;
    private String type;
    private String status;
    private double cost;
    private String date;
}
