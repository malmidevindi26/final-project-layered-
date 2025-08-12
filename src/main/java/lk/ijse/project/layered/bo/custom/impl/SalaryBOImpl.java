package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.SalaryBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.SalaryDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.SalaryDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.SalaryEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaryBOImpl implements SalaryBO {
    private final SalaryDAO salaryDAO = DAOFactory.getInstance().getDAO(DAOType.SALARY);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<SalaryDto> getAllSalaries() throws SQLException, ClassNotFoundException {
        List<SalaryEntity> salaryEntities = salaryDAO.getAll();
        List<SalaryDto> salaryDtos = new ArrayList<>();
        for (SalaryEntity salaryEntity : salaryEntities) {
            salaryDtos.add(converter.getSalaryDTO(salaryEntity));

        }
        return salaryDtos;
    }

    @Override
    public boolean saveSalary(SalaryDto dto) throws Exception, DuplicateException {
        Optional<SalaryEntity> optionalSalary = salaryDAO.findById(dto.getSalaryId());
        if (optionalSalary.isPresent()) {
            throw new DuplicateException("Duplicate salary id");
        }

        SalaryEntity salaryEntity = converter.getSalary(dto);

        boolean save = salaryDAO.save(salaryEntity);
        return save;
    }

    @Override
    public boolean updateSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        Optional<SalaryEntity> optionalSalary = salaryDAO.findById(dto.getSalaryId());
        if(optionalSalary.isEmpty()){
            throw new NotFoundException("Salary not found");
        }

        SalaryEntity salaryEntity = converter.getSalary(dto);
         return salaryDAO.update(salaryEntity);
    }

    @Override
    public boolean deleteSalary(String id) throws Exception, InUseException {
        Optional<SalaryEntity> optionalSalary = salaryDAO.findById(id);
        if(optionalSalary.isEmpty()){
            throw new NotFoundException("SaalryId  not found");
        }

        try {
            boolean delete = salaryDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
//        String lastId = salaryDAO.getLastId();
//        String tableChar = "SA";
//        if (lastId != null) {
//            String lastIdNumberString = lastId.substring(2);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            return String.format(tableChar + "%03d", nextIdNumber);
//        }
//        return tableChar + "001";
        return salaryDAO.getLastId();
    }
}
