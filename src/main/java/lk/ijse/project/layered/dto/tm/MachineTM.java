package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MachineTM {
    private String machineId;
    private String type;
    private String status;
    private double cost;
    private String date;
}
