package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.EmployeeBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.EmployeeDAO;
import lk.ijse.project.layered.dto.EmployeeDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.EmployeeEntity;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeBOImpl implements EmployeeBO {
    private final EmployeeDAO employeeDAO = DAOFactory.getInstance().getDAO(DAOType.EMPLOYEE);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        List<EmployeeEntity> employeeEntities = employeeDAO.getAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            employeeDtos.add(converter.getEmployeeDTO(employeeEntity));

        }
        return employeeDtos;
    }

    @Override
    public void saveEmployee(EmployeeDto dto) throws Exception, DuplicateException {
        Optional<EmployeeEntity> optionalEmployee = employeeDAO.findById(dto.getEmployeeId());
        if (optionalEmployee.isPresent()) {
            throw new DuplicateException("Duplicate employee id");
        }

        EmployeeEntity employeeEntity = converter.getEmployee(dto);

        boolean save = employeeDAO.save(employeeEntity);

    }

    @Override
    public void updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        Optional<EmployeeEntity> optionalEmployee = employeeDAO.findById(dto.getEmployeeId());
        if(optionalEmployee.isEmpty()){
            throw new NotFoundException("Employee not found");
        }

        EmployeeEntity employeeEntity = converter.getEmployee(dto);
        employeeDAO.update(employeeEntity);
    }

    @Override
    public boolean deleteEmployee(String id) throws Exception, InUseException {
        Optional<EmployeeEntity> optionalEmployee = employeeDAO.findById(id);
        if(optionalEmployee.isEmpty()){
            throw new NotFoundException("Employee not found");
        }

        try {
            boolean delete = employeeDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws Exception {
//        String lastId = employeeDAO.getLastId();
//        char tableChar = 'E';
//        if (lastId != null) {
//            String lastIdNumberString = lastId.substring(1);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            return String.format(tableChar + "%03d", nextIdNumber);
//        }
//        return tableChar + "001";
        return employeeDAO.getLastId();
    }

    @Override
    public List<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllIds();
    }
}
