package lk.ijse.project.layered.dao.custom.impl;
import lk.ijse.project.layered.dao.custom.OrderItemDAO;
import lk.ijse.project.layered.entity.OrderEntity;
import lk.ijse.project.layered.entity.OrderItemEntity;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public List<OrderItemEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

//    @Override
//    public boolean save(OrderItemEntity orderEntity) throws SQLException {
//        return false;
//    }

    @Override
    public boolean save(OrderItemEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO OrderItem (item_id, order_id, qty, price) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, entity.getItemId(), entity.getOrderId(), entity.getQty(), entity.getPrice());
    }


    @Override
    public boolean update(OrderItemEntity orderEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<OrderItemEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public double getTotalItemCost(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(qty * price) FROM OrderItem WHERE order_id = ?", orderId);
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0.0;
    }
}
