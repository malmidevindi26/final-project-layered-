package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.UtilityExpenseBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.UtilityExpenseDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.UtilityExpenseDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.UtilityExpenseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilityExpenseBOImpl implements UtilityExpenseBO {
    private final UtilityExpenseDAO utilityExpenseDAO = DAOFactory.getInstance().getDAO(DAOType.UTILITY_EXPENSE);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<UtilityExpenseDto> getAllExpenses() throws SQLException, ClassNotFoundException {
        List<UtilityExpenseEntity> utilityExpenseEntities = utilityExpenseDAO.getAll();
        List<UtilityExpenseDto> utilityExpenseDtos = new ArrayList<>();
        for (UtilityExpenseEntity utilityExpenseEntity : utilityExpenseEntities) {
            utilityExpenseDtos.add(converter.getUtilityExpenseDTO(utilityExpenseEntity));

        }
        return utilityExpenseDtos;
    }

    @Override
    public boolean saveExpense(UtilityExpenseDto dto) throws Exception, DuplicateException {
        Optional<UtilityExpenseEntity> optionalUtilityExpense = utilityExpenseDAO.findById(dto.getExpenseId());
        if (optionalUtilityExpense.isPresent()) {
            throw new DuplicateException("Duplicate Expense id");
        }

        UtilityExpenseEntity utilityExpenseEntity = converter.getUtilityExpense(dto);

        boolean save = utilityExpenseDAO.save(utilityExpenseEntity);
        return  save;
    }

    @Override
    public boolean updateExpense(UtilityExpenseDto dto) throws SQLException, ClassNotFoundException {
        Optional<UtilityExpenseEntity> optionalUtilityExpense = utilityExpenseDAO.findById(dto.getExpenseId());
        if(optionalUtilityExpense.isEmpty()){
            throw new NotFoundException("Expense not found");
        }

        UtilityExpenseEntity utilityExpenseEntity = converter.getUtilityExpense(dto);
        return utilityExpenseDAO.update(utilityExpenseEntity);
    }

    @Override
    public boolean deleteExpense(String id) throws Exception, InUseException {
        Optional<UtilityExpenseEntity> optionalUtilityExpense = utilityExpenseDAO.findById(id);
        if(optionalUtilityExpense.isEmpty()){
            throw new NotFoundException("Expense not found");
        }

        try {
            boolean delete = utilityExpenseDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = utilityExpenseDAO.getLastId();
        char tableChar = 'u';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
