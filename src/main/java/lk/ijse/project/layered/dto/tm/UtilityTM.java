package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UtilityTM {
  private String UtilityId;
  private String type;
  private double amount;
  private String billingDate;

}
