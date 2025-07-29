package lk.ijse.project.layered.dao.custom.impl;

import lk.ijse.project.layered.dao.SQLUtil;
import lk.ijse.project.layered.dao.custom.UtilityExpenseDAO;
import lk.ijse.project.layered.entity.UtilityExpenseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilityExpenseDAOImpl implements UtilityExpenseDAO {
    @Override
    public List<UtilityExpenseEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from UtilityExpense ");

        List<UtilityExpenseEntity> list = new ArrayList<>();
        while (rst.next()) {
            UtilityExpenseEntity utilityExpenseEntity = new UtilityExpenseEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getDate(4)

            );
            list.add(utilityExpenseEntity);
        }
        return list;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select expense_id from UtilityExpense order by expense_id desc limit 1");
        char tableChar = 'U';

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "U001";
    }

    @Override
    public boolean save(UtilityExpenseEntity utilityExpenseEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into UtilityExpense values (?,?,?,?)",
                utilityExpenseEntity.getId(),
                utilityExpenseEntity.getType(),
                utilityExpenseEntity.getAmount(),
                utilityExpenseEntity.getDate()
        );
    }

    @Override
    public boolean update(UtilityExpenseEntity utilityExpenseEntity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update UtilityExpense set expense_type = ?, amount = ?, billing_date = ? where expense_id = ?",
                utilityExpenseEntity.getType(),
                utilityExpenseEntity.getAmount(),
                utilityExpenseEntity.getDate(),
                utilityExpenseEntity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from UtilityExpense where expense_id =?", id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select expense_id from UtilityExpense");
        List<String> list = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<UtilityExpenseEntity> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from UtilityExpense where expense_id = ?", id);
        if(rst.next()){
            return Optional.of(new UtilityExpenseEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getDate(4)
            ));
        }
        return Optional.empty();
    }
}
