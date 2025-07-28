package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.SupplierDAO;
import lk.ijse.project.layered.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<SupplierEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(SupplierEntity supplierEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(SupplierEntity supplierEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<SupplierEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
