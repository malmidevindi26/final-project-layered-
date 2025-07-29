package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.OrderServiceDAO;
import lk.ijse.project.layered.entity.OrderServiceEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderServiceDAOImpl implements OrderServiceDAO {

    @Override
    public List<OrderServiceEntity> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean save(OrderServiceEntity orderServiceEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO OrderService VALUES (?, ?)",
                orderServiceEntity.getOrderId(),
                orderServiceEntity.getServiceId()
        );
    }

    @Override
    public boolean update(OrderServiceEntity orderServiceEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update OrderService set service_id = ? where order_id =?",
                orderServiceEntity.getServiceId(),
                orderServiceEntity.getOrderId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "delete from OrderService where service_id = ? or order_id =?", id
        );
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public Optional<OrderServiceEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from OrderService where service_id = ? or order_id = ?", id);
        if(rst.next()){
            return Optional.of(new OrderServiceEntity(
                    rst.getString(1),
                    rst.getString(2)

            ));
        }
        return Optional.empty();
    }
}
