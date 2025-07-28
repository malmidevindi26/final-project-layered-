package lk.ijse.project.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionEntity {
    private String id;
    private String code;
    private int discountPercent;
    private Date expirationDate;
    private String description;
}
