package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.SalaryDto;
import lk.ijse.project.layered.dto.ServiceDto;

import java.sql.SQLException;
import java.util.List;

public interface ServiceBO extends SuperBO {
    List<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException;

    boolean saveService(ServiceDto dto) throws Exception, DuplicateException;

    boolean updateService(ServiceDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteService(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
