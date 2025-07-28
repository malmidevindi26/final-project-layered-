package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.CustomerDAO;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<CustomerEntity> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("select * from Customer");

        List<CustomerEntity> list = new ArrayList<>();
        while(rst.next()){
            CustomerEntity customerEntity =  new CustomerEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            list.add(customerEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customer_id from Customer order by customer_id desc limit 1");

        char tableChar = 'C';

        if(rst.next()){
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;

        }
        return "C001";
    }

    @Override
    public boolean save(CustomerEntity customerEntity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Customer values (?,?,?,?,?)",
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getAddress(),
                customerEntity.getPhone(),
                customerEntity.getEmail()
        );
    }

    @Override
    public boolean update(CustomerEntity customerEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Customer set  name = ?, address = ?, contact = ?, email = ? where customer_id = ? ",
                customerEntity.getName(),
                customerEntity.getAddress(),
                customerEntity.getPhone(),
                customerEntity.getEmail(),
                customerEntity.getId()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "delete from Customer where customer_id = ? ", id
        );
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customer_id from Customer");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<CustomerEntity> findById(String id) throws SQLException, ClassNotFoundException {
       ResultSet rst = SQLUtil.execute("select * from Customer where customer_id = ?", id);
       if(rst.next()){
           return Optional.of(new CustomerEntity(
                   rst.getString(1),
                   rst.getString(2),
                   rst.getString(3),
                   rst.getString(4),
                   rst.getString(5)
           ));
       }
       return Optional.empty();
    }

}
