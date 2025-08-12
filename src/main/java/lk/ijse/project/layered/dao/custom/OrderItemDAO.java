package lk.ijse.project.layered.dao.custom;

import lk.ijse.project.layered.dao.CrudDAO;
import lk.ijse.project.layered.entity.OrderEntity;
import lk.ijse.project.layered.entity.OrderItemEntity;

import java.sql.SQLException;

public interface OrderItemDAO extends CrudDAO<OrderItemEntity> {
     double getTotalItemCost(String orderId) throws SQLException, ClassNotFoundException;
}
