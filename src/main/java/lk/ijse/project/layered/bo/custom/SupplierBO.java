package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.StoreManagementDto;
import lk.ijse.project.layered.dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    List<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException;

    void saveSupplier(SupplierDto dto) throws Exception, DuplicateException;

    void updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteSupplier(String id) throws Exception, InUseException;

    String getNextId();
}
