package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.custom.PromotionDAO;
import lk.ijse.project.layered.entity.PromotionEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PromotionDAOImpl implements PromotionDAO {
    @Override
    public List<PromotionEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(PromotionEntity promotionEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PromotionEntity promotionEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(PromotionEntity promotionEntity) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<PromotionEntity> findById(String id) throws SQLException {
        return Optional.empty();
    }
}
