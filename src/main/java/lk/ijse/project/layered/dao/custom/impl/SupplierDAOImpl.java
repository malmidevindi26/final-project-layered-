package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.SupplierDAO;
import lk.ijse.project.layered.entity.SupplierEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<SupplierEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Supplier");
        List<SupplierEntity> list = new ArrayList<>();
        while (rst.next()) {
            SupplierEntity supplierEntity = new SupplierEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );
            list.add(supplierEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplier_id from Supplier order by supplier_id desc limit 1" );
        String tableString = "SP";
        while (rst.next()) {
            String lastId = rst.getString(1);
            String lastNumberStrinf = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastNumberStrinf);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString +"%03d",nextIdNumber);
            return nextIdString;
        }
        return "SP001";
    }

    @Override
    public boolean save(SupplierEntity supplierEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Supplier values (?,?,?,?,?,?,?) ",
                supplierEntity.getSupplierId(),
                supplierEntity.getItemId(),
                supplierEntity.getName(),
                supplierEntity.getAddress(),
                supplierEntity.getContact(),
                supplierEntity.getAmount(),
                supplierEntity.getDate()
        );
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Supplier set item_id = ?, name = ?, address = ?, contact = ?, amount = ?, date = ? where supplier_id = ?",
                supplierEntity.getItemId(),
                supplierEntity.getName(),
                supplierEntity.getAddress(),
                supplierEntity.getContact(),
                supplierEntity.getAmount(),
                supplierEntity.getDate(),
                supplierEntity.getSupplierId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Supplier where supplier_id = ? ",id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplier_id from Supplier");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<SupplierEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Supplier where supplier_id = ?", id);
        if(rst.next()){
            return Optional.of(new SupplierEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7)
            ));
        }
        return Optional.empty();
    }
}
