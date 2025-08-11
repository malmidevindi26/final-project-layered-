package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.PaymentBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.PaymentDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.PaymentDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.PaymentEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {
    private final PaymentDAO paymentDAO = DAOFactory.getInstance().getDAO(DAOType.PAYMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        List<PaymentEntity> paymentEntities = paymentDAO.getAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (PaymentEntity paymentEntity : paymentEntities) {
            paymentDtos.add(converter.getPaymentDTO(paymentEntity));

        }
        return paymentDtos;
    }

    @Override
    public void savePayment(PaymentDto dto) throws Exception, DuplicateException {

        Optional<PaymentEntity> optionaPayment = paymentDAO.findById(dto.getPaymentId());
        if (optionaPayment.isPresent()) {
            throw new DuplicateException("Duplicate Payment id");
        }

        PaymentEntity paymentEntity = converter.getPayment(dto);

        boolean save = paymentDAO.save(paymentEntity);
    }

    @Override
    public void updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {

        Optional<PaymentEntity> optionalPayment = paymentDAO.findById(dto.getPaymentId());
        if(optionalPayment.isEmpty()){
            throw new NotFoundException("Payment not found");
        }

        PaymentEntity paymentEntity = converter.getPayment(dto);
        paymentDAO.update(paymentEntity);

    }

    @Override
    public boolean deletePayment(String id) throws Exception, InUseException {
        Optional<PaymentEntity> optionalPayment = paymentDAO.findById(id);
        if(optionalPayment.isEmpty()){
            throw new NotFoundException("Payment not found");
        }

        try {
            boolean delete = paymentDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = paymentDAO.getLastId();
        char tableChar = 'P';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
