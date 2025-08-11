package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.InventoryDAO;
import lk.ijse.project.layered.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public List<InventoryEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Inventory");

        List<InventoryEntity> list = new ArrayList<>();
        while (rst.next()) {
            InventoryEntity inventoryEntity = new InventoryEntity(
                    rst.getString(1),
                    //  rst.getString(2),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getDouble(7)
            );
            list.add(inventoryEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select item_id from Inventory order by item_id desc limit 1");

        String tableString = "I";
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextId = String.format(tableString + "%03d",nextIdNumber);
            return nextId;
        }
        return "I001";
    }

    @Override
    public boolean save(InventoryEntity inventoryEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Inventory values (?,?,?,?,?,?,?)",
                inventoryEntity.getId(),
                //inventoryDto.getItemId(),
                inventoryEntity.getName(),
                inventoryEntity.getManuDate(),
                inventoryEntity.getExpiryDate(),
                inventoryEntity.getStatus(),
                inventoryEntity.getQty(),
                inventoryEntity.getPrice()
        );
    }

    @Override
    public boolean update(InventoryEntity inventoryEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Inventory set item_name = ?, manufactured_date = ?, expaire_date = ?, status = ?, quantity = ?, price = ? where item_id = ?",
                inventoryEntity.getName(),
                inventoryEntity.getManuDate(),
                inventoryEntity.getExpiryDate(),
                inventoryEntity.getStatus(),
                inventoryEntity.getQty(),
                inventoryEntity.getPrice(),
                inventoryEntity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Inventory where item_id = ?",id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select item_id from Inventory");
        List<String> list = new ArrayList<>();
        while (rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<InventoryEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Inventory where item_id = ?", id);
        if(rst.next()){
            return Optional.of(new InventoryEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getDouble(7)
            ));
        }
        return Optional.empty();
    }

    @Override
    public boolean reduceQuantity(String id, int qty) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Inventory set quantity = quantity - ? where item_id = ?", qty, id);
    }
}
