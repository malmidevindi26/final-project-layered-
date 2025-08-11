package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.PenaltyDto;
import lk.ijse.project.layered.dto.PromotionDto;

import java.sql.SQLException;
import java.util.List;

public interface PromotionBO extends SuperBO {
    List<PromotionDto> getAllPromotions() throws SQLException, ClassNotFoundException;

    void savePromotion(PromotionDto dto) throws Exception, DuplicateException;

    void updatePromotion(PromotionDto dto) throws SQLException, ClassNotFoundException;

    boolean deletePromotion(String id) throws Exception, InUseException;

    String getNextId();
}
