package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.PenaltyBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.PenaltyDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.PenaltyDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.PenaltyEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PenaltyBOImpl implements PenaltyBO {
    private PenaltyDAO penaltyDAO = DAOFactory.getInstance().getDAO(DAOType.PENALTY);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<PenaltyDto> getAllPenalties() throws SQLException, ClassNotFoundException {
        List<PenaltyEntity> penaltyEntities = penaltyDAO.getAll();
        List<PenaltyDto> penaltyrDtos = new ArrayList<>();
        for (PenaltyEntity penaltyEntity : penaltyEntities) {
            penaltyrDtos.add(converter.getPenaltyDTO(penaltyEntity));

        }
        return penaltyrDtos;
    }

    @Override
    public boolean savePenalty(PenaltyDto dto) throws Exception, DuplicateException {
        Optional<PenaltyEntity> optionalPenalty = penaltyDAO.findById(dto.getPenaltyId());
        if (optionalPenalty.isPresent()) {
            throw new DuplicateException("Duplicate penalty id");
        }

        PenaltyEntity penaltyEntity = converter.getPenalty(dto);

        boolean save = penaltyDAO.save(penaltyEntity);
        return save;
    }

    @Override
    public boolean updatePenalty(PenaltyDto dto) throws SQLException, ClassNotFoundException {
        Optional<PenaltyEntity> optionalPenalty = penaltyDAO.findById(dto.getPenaltyId());
        if(optionalPenalty.isEmpty()){
            throw new NotFoundException("Penalty not found");
        }

        PenaltyEntity penaltyEntity = converter.getPenalty(dto);
         return penaltyDAO.update(penaltyEntity);
    }

    @Override
    public boolean deletePenalty(String id) throws Exception, InUseException {
        Optional<PenaltyEntity> optionalPenalty = penaltyDAO.findById(id);
        if(optionalPenalty.isEmpty()){
            throw new NotFoundException("Penalty not found");
        }

        try {
            boolean delete = penaltyDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = penaltyDAO.getLastId();
        String tableChar = "PE";
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }

    @Override
    public String getPenaltyIdByOrderIdAndDate(String orderId, String date) throws SQLException, ClassNotFoundException {
        return penaltyDAO.getPenaltyIdByOrderIdAndDate(orderId, date);
    }
}
