package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDto {
    private String promotionId;
    private String code;
    private int discount;
    private String date;
    private String description;

}
