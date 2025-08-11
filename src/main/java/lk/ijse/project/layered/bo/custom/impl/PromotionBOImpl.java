package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.PromotionBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.PromotionDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.PromotionDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.PromotionEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromotionBOImpl implements PromotionBO {
    private final PromotionDAO promotionDAO = DAOFactory.getInstance().getDAO(DAOType.PROMOTION);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<PromotionDto> getAllPromotions() throws SQLException, ClassNotFoundException {
        List<PromotionEntity> promotionEntities = promotionDAO.getAll();
        List<PromotionDto> promotionDtos = new ArrayList<>();
        for (PromotionEntity promotionEntity : promotionEntities) {
            promotionDtos.add(converter.getPromotionDTO(promotionEntity));

        }
        return promotionDtos;
    }

    @Override
    public boolean savePromotion(PromotionDto dto) throws Exception, DuplicateException {
        Optional<PromotionEntity> optionalPromotion = promotionDAO.findById(dto.getPromotionId());
        if (optionalPromotion.isPresent()) {
            throw new DuplicateException("Duplicate promotion id");
        }

        PromotionEntity promotionEntity = converter.getPromotion(dto);

        boolean save = promotionDAO.save(promotionEntity);
        return  save;

    }

    @Override
    public boolean updatePromotion(PromotionDto dto) throws SQLException, ClassNotFoundException {
        Optional<PromotionEntity> optionalPromotion = promotionDAO.findById(dto.getPromotionId());
        if(optionalPromotion.isEmpty()){
            throw new NotFoundException("Promotion not found");
        }

        PromotionEntity promotionEntity = converter.getPromotion(dto);
         return promotionDAO.update(promotionEntity);
    }

    @Override
    public boolean deletePromotion(String id) throws Exception, InUseException {
        Optional<PromotionEntity> optionalPromotion = promotionDAO.findById(id);
        if(optionalPromotion.isEmpty()){
            throw new NotFoundException("promotion not found");
        }

        try {
            boolean delete = promotionDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = promotionDAO.getLastId();
        String tableChar = "C";
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
