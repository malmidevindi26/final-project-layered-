package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.OrderDAO;
import lk.ijse.project.layered.entity.OrderEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<OrderEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from `order`");

        List <OrderEntity> list = new ArrayList<>();
        while (rst.next()) {
            OrderEntity OrderEntity = new OrderEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getBigDecimal(7)
            );
            list.add(OrderEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select order_id from `order` order by order_id desc limit 1");
        char tableChar = 'O';

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lasstIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lasstIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString  = String.format("%s%03d", tableChar, nextIdNumber);
            return nextIdString;
        }
        return "O001";
    }

    @Override
    public boolean save(OrderEntity orderEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into `order` values (?,?,?,?,?,?,?)",
                orderEntity.getOrderId(),
                orderEntity.getCustomerId(),
                orderEntity.getCategory(),
                orderEntity.getDate(),
                orderEntity.getStatus(),
                orderEntity.getServiceId(),
                orderEntity.getCapacity()
        );
    }

    @Override
    public boolean update(OrderEntity orderEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update `order` set customer_id = ?, clothing_category = ?, order_date = ?, status = ?, service_id = ?,Capacity = ? where order_id = ?",
                orderEntity.getCustomerId(),
                orderEntity.getCategory(),
                orderEntity.getDate(),
                orderEntity.getStatus(),
                orderEntity.getServiceId(),
                orderEntity.getCapacity(),
                orderEntity.getOrderId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from `order` where order_id = ? " , id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select order_id from `order`");
        List<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<OrderEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from `order` where order_id = ?", id);
        if(rst.next()){
            return Optional.of(new OrderEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getBigDecimal(7)
            ));
        }
        return Optional.empty();
    }
}
