package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.PenaltyDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PenaltyModel {
    public boolean savePenalty(PenaltyDto penaltyDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Penalty values (?,?,?,?,?)",
                penaltyDto.getPenaltyId(),
                penaltyDto.getOrderId(),
                penaltyDto.getStoreId(),
                penaltyDto.getAmount(),
                penaltyDto.getReason()
         );
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select penalty_id from Penalty order by penalty_id desc limit 1");
        String tableString = "PE";
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberStrinf = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberStrinf);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "PE001";
    }

    public ArrayList<PenaltyDto> getAllPenalty() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Penalty");
        ArrayList<PenaltyDto> list = new ArrayList<>();
        while (rst.next()) {
            PenaltyDto penaltyDto = new PenaltyDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            list.add(penaltyDto);
        }
        return list;
    }

    public boolean updatePenalty(PenaltyDto penaltyDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update Penalty set order_id = ?,store_id = ?, amount = ?, reason = ? where penalty_id = ?",
                penaltyDto.getOrderId(),
                penaltyDto.getStoreId(),
                penaltyDto.getAmount(),
                penaltyDto.getReason(),
                penaltyDto.getPenaltyId()
         );
    }

    public boolean deletePenalty(String penaltyId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Penalty where penalty_id = ?", penaltyId);
    }

    public double getPenaltyAmount(String orderId, String paymentDate) throws SQLException, ClassNotFoundException {
        String sql = "SELECT order_date FROM `order` WHERE order_id = ?";
        ResultSet rs = CrudUtil.execute(sql, orderId);

        if (rs.next()) {
            LocalDate orderDate = rs.getDate("order_date").toLocalDate();
            LocalDate payDate = LocalDate.parse(paymentDate);

            long daysBetween = ChronoUnit.DAYS.between(orderDate, payDate);

            if (daysBetween <= 7) {
                return 0.0;
            } else {
                return (daysBetween - 7) * 20.0;
            }
        }

        return 0.0;
    }

    public String getPenaltyIdByOrderIdAndDate(String orderId, String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT penalty_id FROM Penalty WHERE order_id = ? AND reason = ?", orderId, date);
        if (rst.next()) {
            return rst.getString("penalty_id");
        }
        return null;
    }
}
