package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.OrderDto;
import lk.ijse.project.layered.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException;

    boolean savePayment(PaymentDto dto) throws Exception, DuplicateException;

    boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException;

    boolean deletePayment(String id) throws Exception, InUseException;

    String getNextId() throws SQLException, ClassNotFoundException;
}
