package lk.ijse.project.layered.dao.custom;

import lk.ijse.project.layered.dao.CrudDAO;
import lk.ijse.project.layered.dto.OrderServiceDto;
import lk.ijse.project.layered.entity.OrderEntity;
import lk.ijse.project.layered.entity.OrderServiceEntity;

import java.sql.SQLException;

public interface OrderServiceDAO extends CrudDAO<OrderServiceEntity> {

    boolean saveOrderService(OrderServiceDto dto) throws SQLException, ClassNotFoundException;

    boolean updateOrderService(OrderServiceDto orderServiceDto) throws SQLException, ClassNotFoundException;
}
