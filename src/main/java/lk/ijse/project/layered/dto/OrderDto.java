package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDto {
    private String orderId;
    private String customId;
    private String clothCategory;
    private String date;
    private String status;
    private String serviceId;
    private double capacity;

    public OrderDto(String string, String string1, String string2) {
        this.orderId = string;
        this.customId = string1;
        this.clothCategory = string2;
    }
}
