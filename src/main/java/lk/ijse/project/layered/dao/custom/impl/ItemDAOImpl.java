package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.ItemDAO;
import lk.ijse.project.layered.entity.ItemEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public List<ItemEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(ItemEntity itemEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(ItemEntity itemEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(ItemEntity itemEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<ItemEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
