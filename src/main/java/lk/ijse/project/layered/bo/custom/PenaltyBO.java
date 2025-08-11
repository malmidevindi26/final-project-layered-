package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.PaymentDto;
import lk.ijse.project.layered.dto.PenaltyDto;

import java.sql.SQLException;
import java.util.List;

public interface PenaltyBO extends SuperBO {
    List<PenaltyDto> getAllPenalties() throws SQLException, ClassNotFoundException;

    boolean savePenalty(PenaltyDto dto) throws Exception, DuplicateException;

    boolean updatePenalty(PenaltyDto dto) throws SQLException, ClassNotFoundException;

    boolean deletePenalty(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
