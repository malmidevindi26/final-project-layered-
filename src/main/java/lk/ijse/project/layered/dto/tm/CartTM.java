package lk.ijse.project.layered.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartTM {
    private String itemId;
    private String itemName;
    private double cartQty;
    private double unitPrice;
    private double total;
    private Button btnRemove;
}
