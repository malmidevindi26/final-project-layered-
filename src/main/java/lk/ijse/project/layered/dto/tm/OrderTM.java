package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderTM {
    private String orderId;
    private String customerId;
    private String category;
    private String date;
    private String status;
    private String serviceId;
}
