package lk.ijse.project.layered.dao.custom.impl;
import lk.ijse.project.layered.dao.custom.OrderItemDAO;
import lk.ijse.project.layered.entity.OrderEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public List<OrderEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(OrderEntity orderEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderEntity orderEntity) throws SQLException {
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
    public Optional<OrderEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
