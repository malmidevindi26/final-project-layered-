package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.MachineDto;
import lk.ijse.project.layered.dto.OrderDto;
import lk.ijse.project.layered.dto.OrderServiceDto;
import lk.ijse.project.layered.entity.OrderEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderBO extends SuperBO {
    List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDto dto) throws Exception, DuplicateException;

    boolean updateOrder(OrderDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;

    boolean saveOrderService(OrderServiceDto dto) throws SQLException, ClassNotFoundException;

    boolean updateOrderService(OrderServiceDto orderServiceDto) throws SQLException, ClassNotFoundException;

    List<String> getAllOrderIds() throws SQLException, ClassNotFoundException;

//    Optional<OrderEntity> findById(String id) throws SQLException, ClassNotFoundException;

    OrderDto findById(String id) throws SQLException, ClassNotFoundException;

    double getTotalItemCost(String orderId) throws SQLException, ClassNotFoundException;

    double getTotalServiceCost(String orderId) throws SQLException, ClassNotFoundException;

    double getPenaltyAmount(String orderId, String paymentDate) throws SQLException, ClassNotFoundException;
}
