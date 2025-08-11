package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.dto.ItemDto;
import lk.ijse.project.layered.dto.OrderDto;

import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    boolean placeOrder(ItemDto dto) throws SQLException, ClassNotFoundException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
