package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.PaymentDAO;
import lk.ijse.project.layered.entity.PaymentEntity;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public List<PaymentEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Payment");
        List<PaymentEntity> list = new ArrayList<>();
        while (rst.next()) {
            PaymentEntity paymentEntity = new PaymentEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
            list.add(paymentEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select payment_id from Payment order by payment_id desc limit 1");
        char tableChar = 'P';
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "P001";
    }

    @Override
    public boolean save(PaymentEntity paymentEntity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Payment values (?,?,?,?,?,?,?,?)",
                paymentEntity.getPaymentId(),
                paymentEntity.getOrderId(),
                paymentEntity.getPromotionAmount(),
                paymentEntity.getPenaltyAmount(),
                paymentEntity.getTotalAmount(),
                paymentEntity.getMethod(),
                paymentEntity.getStatus(),
                paymentEntity.getDate()
        );
    }

    @Override
    public boolean update(PaymentEntity paymentEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Payment set order_id = ?, promotion_amount = ?, penalty_amount = ?, amount = ?, payment_method = ?, status = ?, date = ? where payment_id = ?",
                paymentEntity.getOrderId(),
                paymentEntity.getPromotionAmount(),
                paymentEntity.getPenaltyAmount(),
                paymentEntity.getTotalAmount(),
                paymentEntity.getMethod(),
                paymentEntity.getStatus(),
                paymentEntity.getDate(),
                paymentEntity.getPaymentId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Payment where payment_id = ?", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select payment_id from Payment");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<PaymentEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Payment where payment_id = ?", id);
        if(rst.next()){
            return Optional.of(new PaymentEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }
        return Optional.empty();
    }
}
