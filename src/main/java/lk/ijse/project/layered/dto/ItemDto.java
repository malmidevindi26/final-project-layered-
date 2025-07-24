package lk.ijse.project.layered.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemDto {
  private String orderId;
  private String customerId;
  private Date date;
  private ArrayList<OrderItemDto> cartList;


  public ItemDto(String selectedOrderId, String customerId, Date date, ArrayList<OrderItemDto> cartList) {
    this.orderId = selectedOrderId;
    this.customerId = customerId;
    this.date = date;
    this.cartList = cartList;

  }
}
