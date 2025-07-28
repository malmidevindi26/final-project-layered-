package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.PenaltyDAO;
import lk.ijse.project.layered.entity.PenaltyEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PenaltyDAOImpl implements PenaltyDAO {
    @Override
    public List<PenaltyEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(PenaltyEntity penaltyEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PenaltyEntity penaltyEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(PenaltyEntity penaltyEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<PenaltyEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
