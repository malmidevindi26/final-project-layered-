package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.ServiceDAO;
import lk.ijse.project.layered.entity.ServiceEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceDAOImpl implements ServiceDAO {
    @Override
    public List<ServiceEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(ServiceEntity serviceEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(ServiceEntity serviceEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(ServiceEntity serviceEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<ServiceEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
