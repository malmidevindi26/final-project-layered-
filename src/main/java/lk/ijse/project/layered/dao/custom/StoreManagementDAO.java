package lk.ijse.project.layered.dao.custom;

import lk.ijse.project.layered.dao.CrudDAO;
import lk.ijse.project.layered.dto.StoreManagementDto;
import lk.ijse.project.layered.entity.StoreManagementEntity;

import java.sql.SQLException;

public interface StoreManagementDAO extends CrudDAO<StoreManagementEntity> {
    String getStoreId(String orderId) throws SQLException, ClassNotFoundException;

    boolean updateOrInsertStore(StoreManagementEntity dto) throws SQLException, ClassNotFoundException;
}
