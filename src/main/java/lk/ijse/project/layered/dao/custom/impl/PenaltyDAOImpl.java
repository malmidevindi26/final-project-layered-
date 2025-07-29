package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.PenaltyDAO;
import lk.ijse.project.layered.entity.PenaltyEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PenaltyDAOImpl implements PenaltyDAO {
    @Override
    public List<PenaltyEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Penalty");
        List<PenaltyEntity> list = new ArrayList<>();
        while (rst.next()) {
            PenaltyEntity penaltyEntity = new PenaltyEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getString(5)
            );
            list.add(penaltyEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select penalty_id from Penalty order by penalty_id desc limit 1");
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

    @Override
    public boolean save(PenaltyEntity penaltyEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Penalty values (?,?,?,?,?)",
                penaltyEntity.getPenaltyId(),
                penaltyEntity.getOrderId(),
                penaltyEntity.getStoreId(),
                penaltyEntity.getAmount(),
                penaltyEntity.getReason()
        );
    }

    @Override
    public boolean update(PenaltyEntity penaltyEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Penalty set order_id = ?,store_id = ?, amount = ?, reason = ? where penalty_id = ?",
                penaltyEntity.getOrderId(),
                penaltyEntity.getStoreId(),
                penaltyEntity.getAmount(),
                penaltyEntity.getReason(),
                penaltyEntity.getPenaltyId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Penalty where penalty_id = ?", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select penalty_id from Penalty");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<PenaltyEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Penalty where penalty_id = ?", id);
        if(rst.next()){
            return Optional.of(new PenaltyEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getString(5)
            ));
        }
        return Optional.empty();
    }
}
