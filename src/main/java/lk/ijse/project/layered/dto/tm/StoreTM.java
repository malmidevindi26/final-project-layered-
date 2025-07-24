package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreTM {
    private String storeId;
    private String orderId;
    private double capacity;
}
