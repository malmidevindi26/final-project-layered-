package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.SalaryDAO;
import lk.ijse.project.layered.entity.SalaryEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public List<SalaryEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(SalaryEntity salaryEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(SalaryEntity salaryEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(SalaryEntity salaryEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<SalaryEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
