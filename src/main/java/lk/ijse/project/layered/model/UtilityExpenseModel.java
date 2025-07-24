package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.UtilityExpenseDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtilityExpenseModel {
    public boolean saveUtility(UtilityExpenseDto utilityExpenseDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into UtilityExpense values (?,?,?,?)",
                utilityExpenseDto.getExpenseId(),
                utilityExpenseDto.getExpenseType(),
                utilityExpenseDto.getAmount(),
                utilityExpenseDto.getBillingDate()
             );
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select expense_id from UtilityExpense order by expense_id desc limit 1");
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


    public ArrayList<UtilityExpenseDto> getAllUtility() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from UtilityExpense ");

        ArrayList<UtilityExpenseDto> list = new ArrayList<>();
        while (rst.next()) {
            UtilityExpenseDto utilityExpenseDto = new UtilityExpenseDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)

            );
            list.add(utilityExpenseDto);
        }
        return list;
    }

    public boolean updateUtility(UtilityExpenseDto utilityExpenseDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update UtilityExpense set expense_type = ?, amount = ?, billing_date = ? where expense_id = ?",
                utilityExpenseDto.getExpenseType(),
                utilityExpenseDto.getAmount(),
                utilityExpenseDto.getBillingDate(),
                utilityExpenseDto.getExpenseId()
        );
    }

    public boolean deleteUtilityExpense(String utilityId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from UtilityExpense where expense_id =?", utilityId);
    }
}
