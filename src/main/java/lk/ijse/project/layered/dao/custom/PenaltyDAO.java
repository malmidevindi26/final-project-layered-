package lk.ijse.project.layered.dao.custom;

import lk.ijse.project.layered.dao.CrudDAO;
import lk.ijse.project.layered.entity.PenaltyEntity;

import java.sql.SQLException;

public interface PenaltyDAO extends CrudDAO<PenaltyEntity> {

    String getPenaltyIdByOrderIdAndDate(String orderId, String date) throws SQLException, ClassNotFoundException;

    double getPenaltyAmount(String orderId, String paymentDate) throws SQLException, ClassNotFoundException;
}
