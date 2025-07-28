package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.InventoryDAO;
import lk.ijse.project.layered.entity.InventoryEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public List<InventoryEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(InventoryEntity inventoryEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(InventoryEntity inventoryEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(InventoryEntity inventoryEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<InventoryEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
