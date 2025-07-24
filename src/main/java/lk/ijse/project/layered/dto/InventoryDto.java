package lk.ijse.project.layered.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
   // private String  inventoryId;
    private String  itemId;
    private String  name;
    private String manufacturerdDate;
    private String expaireDate;
    private String status;
    private double quantity;
    private double price;

 public InventoryDto(String string, String string1, double anInt, double aDouble) {
  this.itemId = string;
  this.name = string1;
  this.quantity = anInt;
  this.price = aDouble;
 }
}
