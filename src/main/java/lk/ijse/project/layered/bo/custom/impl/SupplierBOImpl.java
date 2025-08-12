package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.SupplierBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.SupplierDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.SupplierDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierBOImpl implements SupplierBO {
    private final SupplierDAO supplierDAO = DAOFactory.getInstance().getDAO(DAOType.SUPPLIER);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        List<SupplierEntity> supplierEntities = supplierDAO.getAll();
        List<SupplierDto> supplierDtos = new ArrayList<>();
        for (SupplierEntity supplierEntity : supplierEntities) {
            supplierDtos.add(converter.getSupplierDTO(supplierEntity));

        }
        return supplierDtos;
    }

    @Override
    public boolean saveSupplier(SupplierDto dto) throws Exception, DuplicateException {
        Optional<SupplierEntity> optionalSupplier = supplierDAO.findById(dto.getSupplierId());
        if (optionalSupplier.isPresent()) {
            throw new DuplicateException("Duplicate supplier id");
        }

        SupplierEntity supplierEntity = converter.getSupplier(dto);

        boolean save = supplierDAO.save(supplierEntity);
        return save;
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        Optional<SupplierEntity> optionalSupplier = supplierDAO.findById(dto.getSupplierId());
        if(optionalSupplier.isEmpty()){
            throw new NotFoundException("Supplier not found");
        }

        SupplierEntity supplierEntity = converter.getSupplier(dto);
        return supplierDAO.update(supplierEntity);
    }

    @Override
    public boolean deleteSupplier(String id) throws Exception, InUseException {
        Optional<SupplierEntity> optionalSupplier = supplierDAO.findById(id);
        if(optionalSupplier.isEmpty()){
            throw new NotFoundException("supplier not found");
        }

        try {
            boolean delete = supplierDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = supplierDAO.getLastId();
        String  tableChar = "SP";
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }

    @Override
    public List<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllIds();
    }
}
