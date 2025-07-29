package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.SalaryDAO;
import lk.ijse.project.layered.entity.SalaryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public List<SalaryEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Salary");
        List<SalaryEntity> list = new ArrayList<>();
        while (rst.next()) {
            SalaryEntity salaryEntity = new SalaryEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getDate(4)
            );
            list.add(salaryEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select salary_id from Salary order by salary_id desc limit 1");
        String salartString = "SL";
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(salartString + "%03d",nextIdNumber);
            return nextIdString;
        }
        return "SL001";
    }

    @Override
    public boolean save(SalaryEntity salaryEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Salary values(?,?,?,?)",
                salaryEntity.getSalaryId(),
                salaryEntity.getEmployeeId(),
                salaryEntity.getSalary(),
                salaryEntity.getIssuedDate()
        );
    }

    @Override
    public boolean update(SalaryEntity salaryEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Salary set employee_id = ?, amount = ?, issue_date = ? where salary_id = ?",
                salaryEntity.getEmployeeId(),
                salaryEntity.getSalary(),
                salaryEntity.getIssuedDate(),
                salaryEntity.getSalaryId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Salary where salary_id = ?",id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select salary_id from Salary");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<SalaryEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Salary where salary_id = ?", id);
        if(rst.next()){
            return Optional.of(new SalaryEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getDate(4)

            ));
        }
        return Optional.empty();
    }
}
