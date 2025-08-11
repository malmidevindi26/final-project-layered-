package lk.ijse.project.layered.dao.custom;

import lk.ijse.project.layered.dao.CrudDAO;
import lk.ijse.project.layered.entity.InventoryEntity;

import java.sql.SQLException;

public interface InventoryDAO extends CrudDAO<InventoryEntity> {
    boolean reduceQuantity(String id, int qty) throws SQLException, ClassNotFoundException;
}
