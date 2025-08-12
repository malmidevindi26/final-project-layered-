package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.StoreManagementBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.StoreManagementDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.StoreManagementDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.StoreManagementEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreManagementBOImpl implements StoreManagementBO {
    private final StoreManagementDAO  storeManagementDAO = DAOFactory.getInstance().getDAO(DAOType.STORE_MANAGEMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<StoreManagementDto> getAllStores() throws SQLException, ClassNotFoundException {
        List<StoreManagementEntity> storeManagementEntities = storeManagementDAO.getAll();
        List<StoreManagementDto> storeManagementDtos = new ArrayList<>();
        for (StoreManagementEntity storeManagementEntity : storeManagementEntities) {
            storeManagementDtos.add(converter.getStoreManagementDTO(storeManagementEntity));

        }
        return storeManagementDtos;
    }

    @Override
    public boolean saveStore(StoreManagementDto dto) throws Exception, DuplicateException {
        Optional<StoreManagementEntity> optionalStoreManagement = storeManagementDAO.findById(dto.getStoreId());
        if (optionalStoreManagement.isPresent()) {
            throw new DuplicateException("Duplicate store id");
        }

        StoreManagementEntity storeManagementEntity = converter.getStoreManagement(dto);

        boolean save = storeManagementDAO.save(storeManagementEntity);
        return save;
    }

    @Override
    public boolean updateStore(StoreManagementDto dto) throws SQLException, ClassNotFoundException {
        Optional<StoreManagementEntity> optionalStoreManagement = storeManagementDAO.findById(dto.getStoreId());
        if(optionalStoreManagement.isEmpty()){
            throw new NotFoundException("Store not found");
        }

        StoreManagementEntity storeManagementEntity = converter.getStoreManagement(dto);
        return storeManagementDAO.update(storeManagementEntity);
    }

    @Override
    public boolean deleteStore(String id) throws Exception, InUseException {
        Optional<StoreManagementEntity> optionalStoreManagement = storeManagementDAO.findById(id);
        if(optionalStoreManagement.isEmpty()){
            throw new NotFoundException("Store not found");
        }

        try {
            boolean delete = storeManagementDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = storeManagementDAO.getLastId();
        String tableChar = "ST";
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }

    @Override
    public boolean updateOrInsertStore(StoreManagementDto entity) throws SQLException, ClassNotFoundException {
        StoreManagementEntity storeManagementEntity = converter.getStoreManagement(entity);
        return storeManagementDAO.updateOrInsertStore(storeManagementEntity);
    }

    @Override
    public String getStoreId(String orderId) throws SQLException, ClassNotFoundException {
        return storeManagementDAO.getStoreId(orderId);
    }

    @Override
    public List<String> getAllStoreIds() throws SQLException, ClassNotFoundException {
        return storeManagementDAO.getAllIds();
    }


//    @Override
//    public boolean updateOrInsertStore(StoreManagementEntity entity) throws SQLException, ClassNotFoundException {
//        return storeManagementDAO.updateOrInsertStore(entity);
//    }
}
