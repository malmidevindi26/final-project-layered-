package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.StoreManagementDAO;
import lk.ijse.project.layered.entity.StoreManagementEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StoreManagementDAOImpl implements StoreManagementDAO {
    @Override
    public List<StoreManagementEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(StoreManagementEntity storeManagementEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(StoreManagementEntity storeManagementEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(StoreManagementEntity storeManagementEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<StoreManagementEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
