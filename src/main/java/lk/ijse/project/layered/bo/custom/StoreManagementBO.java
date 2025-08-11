package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.ServiceDto;
import lk.ijse.project.layered.dto.StoreManagementDto;

import java.sql.SQLException;
import java.util.List;

public interface StoreManagementBO extends SuperBO {
    List<StoreManagementDto> getAllStores() throws SQLException, ClassNotFoundException;

    boolean saveStore(StoreManagementDto dto) throws Exception, DuplicateException;

    boolean updateStore(StoreManagementDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteStore(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
