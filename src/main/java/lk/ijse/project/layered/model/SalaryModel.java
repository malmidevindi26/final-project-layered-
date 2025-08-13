//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.dto.SalaryDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class SalaryModel {
//    public boolean saveSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("insert into Salary values(?,?,?,?)",
//                salaryDto.getSalaryId(),
//                salaryDto.getEmployeeId(),
//                salaryDto.getAmount(),
//                salaryDto.getDate()
//        );
//    }
//
//    public String getNextId() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select salary_id from Salary order by salary_id desc limit 1");
//        String salartString = "SL";
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String lastIdNumberString = lastId.substring(2);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            String nextIdString = String.format(salartString + "%03d",nextIdNumber);
//            return nextIdString;
//        }
//        return "SL001";
//    }
//
//    public ArrayList<SalaryDto> getAllSalary() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select * from Salary");
//        ArrayList<SalaryDto> list = new ArrayList<>();
//        while (rst.next()) {
//            SalaryDto salaryDto = new SalaryDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getDouble(3),
//                    rst.getString(4)
//            );
//            list.add(salaryDto);
//        }
//        return list;
//    }
//
//    public boolean updateSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("update Salary set employee_id = ?, amount = ?, issue_date = ? where salary_id = ?",
//                salaryDto.getEmployeeId(),
//                salaryDto.getAmount(),
//                salaryDto.getDate(),
//                salaryDto.getSalaryId()
//         );
//    }
//
//    public boolean deleteSalary(String salaryId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("delete from Salary where salary_id = ?",salaryId);
//    }
//}
