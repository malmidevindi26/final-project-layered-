package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.StoreManagementDAO;
import lk.ijse.project.layered.entity.StoreManagementEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreManagementDAOImpl implements StoreManagementDAO {
    @Override
    public List<StoreManagementEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from StoreManagement ");
        List<StoreManagementEntity> list = new ArrayList<>();
        while (rst.next()) {
            StoreManagementEntity storeManagementEntity = new StoreManagementEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3)
            );
            list.add(storeManagementEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select store_id from StoreManagement order by store_id desc limit 1");
        String tableString = "ST";
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("%s%03d", tableString, nextIdNumber);
            return nextIdString;
        }
        return "ST001";
    }

    @Override
    public boolean save(StoreManagementEntity storeManagementEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into StoreManagement values (?,?,?)",
                storeManagementEntity.getStoreId(),
                storeManagementEntity.getOrderId(),
                storeManagementEntity.getCapacity()
        );
    }

    @Override
    public boolean update(StoreManagementEntity storeManagementEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update StoreManagement set order_id = ?, capacity = ? where store_id = ?",
                storeManagementEntity.getOrderId(),
                storeManagementEntity.getCapacity(),
                storeManagementEntity.getStoreId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from StoreManagement where store_id = ?", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select store_id from StoreManagement");
        List<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<StoreManagementEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from StoreManagement where store_id = ?", id);
        if(rst.next()){
            return Optional.of(new StoreManagementEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3)

            ));
        }
        return Optional.empty();
    }
}
