package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.PaymentDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Payment values (?,?,?,?,?,?,?,?)",
                paymentDto.getPaymentId(),
                paymentDto.getOrderId(),
                paymentDto.getPromotionAmount(),
                paymentDto.getPenaltyAmount(),
                paymentDto.getAmount(),
                paymentDto.getMethod(),
                paymentDto.getStatus(),
                paymentDto.getDate()
        );
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select payment_id from Payment order by payment_id desc limit 1");
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

    public ArrayList<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Payment");
        ArrayList<PaymentDto> list = new ArrayList<>();
        while (rst.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
            list.add(paymentDto);
        }
        return list;
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update Payment set order_id = ?, promotion_amount = ?, penalty_amount = ?, amount = ?, payment_method = ?, status = ?, date = ? where payment_id = ?",
               paymentDto.getOrderId(),
               paymentDto.getPromotionAmount(),
               paymentDto.getPenaltyAmount(),
               paymentDto.getAmount(),
               paymentDto.getMethod(),
               paymentDto.getStatus(),
               paymentDto.getDate(),
               paymentDto.getPaymentId()
        );
    }

    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Payment where payment_id = ?", paymentId);
    }
}
