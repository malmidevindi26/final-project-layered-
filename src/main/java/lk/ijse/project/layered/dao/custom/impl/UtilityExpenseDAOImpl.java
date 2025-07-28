package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.UtilityExpenseDAO;
import lk.ijse.project.layered.entity.UtilityExpenseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UtilityExpenseDAOImpl implements UtilityExpenseDAO {
    @Override
    public List<UtilityExpenseEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(UtilityExpenseEntity utilityExpenseEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(UtilityExpenseEntity utilityExpenseEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(UtilityExpenseEntity utilityExpenseEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<UtilityExpenseEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
