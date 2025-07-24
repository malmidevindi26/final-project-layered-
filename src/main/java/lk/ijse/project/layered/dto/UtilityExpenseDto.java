package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UtilityExpenseDto {
    private String expenseId;
    private String expenseType;
    private double amount;
    private String billingDate;

}
