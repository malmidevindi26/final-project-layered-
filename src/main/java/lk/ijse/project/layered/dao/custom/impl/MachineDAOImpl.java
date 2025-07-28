package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.MachineDAO;
import lk.ijse.project.layered.entity.MachineEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MachineDAOImpl implements MachineDAO {
    @Override
    public List<MachineEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(MachineEntity machineEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(MachineEntity machineEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(MachineEntity machineEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<MachineEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
