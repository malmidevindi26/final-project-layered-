package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.PromotionDAO;
import lk.ijse.project.layered.entity.PromotionEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromotionDAOImpl implements PromotionDAO {
    @Override
    public List<PromotionEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Promotion");

        List<PromotionEntity> list = new ArrayList<>();
        while (rst.next()) {
            PromotionEntity promotionEntity = new PromotionEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)

            );
            list.add(promotionEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select promotion_id from Promotion order by promotion_id desc limit 1");

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

    @Override
    public boolean save(PromotionEntity promotionEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Promotion values (?,?,?,?,?)",
                promotionEntity.getId(),
                promotionEntity.getCode(),
                promotionEntity.getDiscountPercent(),
                promotionEntity.getExpirationDate(),
                promotionEntity.getDescription()
        );
    }

    @Override
    public boolean update(PromotionEntity promotionEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Promotion set code = ?, discount_percentage = ?, expiry_date = ?, description = ? where promotion_id= ?",
                promotionEntity.getCode(),
                promotionEntity.getDiscountPercent(),
                promotionEntity.getExpirationDate(),
                promotionEntity.getDescription(),
                promotionEntity.getId()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Promotion where promotion_id = ?", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select promotion_id from Promotion");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<PromotionEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Promotion where promotion_id = ?", id);
        if(rst.next()){
            return Optional.of(new PromotionEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return Optional.empty();
    }
}
