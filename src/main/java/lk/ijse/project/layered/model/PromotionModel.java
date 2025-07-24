package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.PromotionDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionModel {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select promotion_id from Promotion order by promotion_id desc limit 1");

        String tableString = "PR";

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "PR001";
    }

    public boolean savePromotion(PromotionDto promotionDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Promotion values (?,?,?,?,?)",
                promotionDto.getPromotionId(),
                promotionDto.getCode(),
                promotionDto.getDiscount(),
                promotionDto.getDate(),
                promotionDto.getDescription()
         );
    }

    public ArrayList<PromotionDto> getAllPromotion() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Promotion");

        ArrayList<PromotionDto> list = new ArrayList<>();
        while (rst.next()) {
            PromotionDto promotionDto = new PromotionDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)

            );
            list.add(promotionDto);
        }
        return list;
    }

    public boolean updatePromotion(PromotionDto promotionDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update Promotion set code = ?, discount_percentage = ?, expiry_date = ?, description = ? where promotion_id= ?",
                promotionDto.getCode(),
                promotionDto.getDiscount(),
                promotionDto.getDate(),
                promotionDto.getDescription(),
                promotionDto.getPromotionId()

        );
    }

    public boolean deletePromotion(String promoId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Promotion where promotion_id = ?", promoId);
    }
}
