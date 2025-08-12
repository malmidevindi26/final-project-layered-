package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.InventoryDto;


import java.sql.SQLException;
import java.util.List;

public interface InventoryBO extends SuperBO {
    List<InventoryDto> getAllInventories() throws SQLException, ClassNotFoundException;

    void saveInventory(InventoryDto dto) throws Exception, DuplicateException;

    void updateInventory(InventoryDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteInventory(String id) throws Exception, InUseException;

    String getNextId() throws Exception;

    List<String> getAllItemIds() throws SQLException, ClassNotFoundException;

    InventoryDto findById(String id) throws SQLException, ClassNotFoundException;

}
