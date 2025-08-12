package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.BOFactory;
import lk.ijse.project.layered.bo.BOType;
import lk.ijse.project.layered.bo.custom.InventoryBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.InventoryDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.InventoryDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.InventoryEntity;
import lk.ijse.project.layered.entity.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryBOImpl implements InventoryBO {
    private final InventoryDAO inventoryDAO = DAOFactory.getInstance().getDAO(DAOType.INVENTORY);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<InventoryDto> getAllInventories() throws SQLException, ClassNotFoundException {
        List<InventoryEntity> inventoryEntities = inventoryDAO.getAll();
        List<InventoryDto> inventoryDtos = new ArrayList<>();
        for (InventoryEntity inventoryEntity : inventoryEntities) {
            inventoryDtos.add(converter.getInventoryDTO(inventoryEntity));

        }
        return inventoryDtos;
    }

    @Override
    public void saveInventory(InventoryDto dto) throws Exception, DuplicateException {
        Optional<InventoryEntity> optionalInventory = inventoryDAO.findById(dto.getItemId());
        if (optionalInventory.isPresent()) {
            throw new DuplicateException("Duplicate inventory id");
        }

        InventoryEntity inventoryEntity = converter.getInventory(dto);

        boolean save = inventoryDAO.save(inventoryEntity);
    }

    @Override
    public void updateInventory(InventoryDto dto) throws SQLException, ClassNotFoundException {
        Optional<InventoryEntity> optionalInventory = inventoryDAO.findById(dto.getItemId());
        if(optionalInventory.isEmpty()){
            throw new NotFoundException("Item not found");
        }

        InventoryEntity inventoryEntity = converter.getInventory(dto);
        inventoryDAO.update(inventoryEntity);
    }

    @Override
    public boolean deleteInventory(String id) throws Exception, InUseException {
        Optional<InventoryEntity> optionalInventory = inventoryDAO.findById(id);
        if(optionalInventory.isEmpty()){
            throw new NotFoundException("Item not found");
        }

        try {
            boolean delete = inventoryDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws Exception {
//        String lastId = inventoryDAO.getLastId();
//        char tableChar = 'I';
//        if (lastId != null) {
//            String lastIdNumberString = lastId.substring(1);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            return String.format(tableChar + "%03d", nextIdNumber);
//        }
//        return tableChar + "001";
        return  inventoryDAO.getLastId();
    }

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return inventoryDAO.getAllIds();
    }

    @Override
    public InventoryDto findById(String id) throws SQLException, ClassNotFoundException {
        Optional<InventoryEntity> optionalInventory = inventoryDAO.findById(id);
        return optionalInventory.map(converter::getInventoryDTO).orElse(null);
    }
}
