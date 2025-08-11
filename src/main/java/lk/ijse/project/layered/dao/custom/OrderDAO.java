package lk.ijse.project.layered.dao.custom;

import lk.ijse.project.layered.dao.CrudDAO;
import lk.ijse.project.layered.entity.OrderEntity;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<OrderEntity> {
    boolean existOrdersByCustomerId(String customerId) throws SQLException, ClassNotFoundException;
}
