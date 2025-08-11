package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.SupplierDto;
import lk.ijse.project.layered.dto.UtilityExpenseDto;

import java.sql.SQLException;
import java.util.List;

public interface UtilityExpenseBO extends SuperBO {
    List<UtilityExpenseDto> getAllExpenses() throws SQLException, ClassNotFoundException;

    boolean saveExpense(UtilityExpenseDto dto) throws Exception, DuplicateException;

    boolean updateExpense(UtilityExpenseDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteExpense(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
