package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.PromotionDto;
import lk.ijse.project.layered.dto.SalaryDto;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    List<SalaryDto> getAllSalaries() throws SQLException, ClassNotFoundException;

    boolean saveSalary(SalaryDto dto) throws Exception, DuplicateException;

    boolean updateSalary(SalaryDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteSalary(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
