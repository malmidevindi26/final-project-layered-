package lk.ijse.project.layered.dto.tm;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionTM {
    private String promotionId;
    private String code;
    private int discount;
    private String date;
    private String description;
}
