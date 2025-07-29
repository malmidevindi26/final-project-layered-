package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.EmployeeDAO;
import lk.ijse.project.layered.entity.EmployeeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<EmployeeEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Employee");

        List<EmployeeEntity> list = new ArrayList<>();
        while (rst.next()) {
            EmployeeEntity employeeEntity = new EmployeeEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBigDecimal(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            list.add(employeeEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
         ResultSet rst = SQLUtil.execute("select employee_id from Employee order by employee_id desc limit 1");

        char tableChar = 'E';

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "E001";
    }

    @Override
    public boolean save(EmployeeEntity employeeEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Employee values (?,?,?,?,?,?,?)",
                employeeEntity.getId(),
                employeeEntity.getName(),
                employeeEntity.getAddress(),
                employeeEntity.getPhone(),
                employeeEntity.getSalary(),
                employeeEntity.getDate(),
                employeeEntity.getRole()
        );
    }

    @Override
    public boolean update(EmployeeEntity employeeEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Employee set name = ? , address = ?, contact = ?, salary = ?, hire_date = ?, role = ? where employee_id = ? ",
                employeeEntity.getName(),
                employeeEntity.getAddress(),
                employeeEntity.getPhone(),
                employeeEntity.getSalary(),
                employeeEntity.getDate(),
                employeeEntity.getRole(),
                employeeEntity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Employee where employee_id = ?", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select employee_id from Employee");
        List<String> list = new ArrayList<>();
        while (rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<EmployeeEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Employee where employee_id = ?", id);
        if(rst.next()){
            return Optional.of(new EmployeeEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getBigDecimal(5),
                    rst.getDate(6),
                    rst.getString(7)
            ));
        }
        return Optional.empty();
    }
}
