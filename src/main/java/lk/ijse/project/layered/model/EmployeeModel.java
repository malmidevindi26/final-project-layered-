//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.dto.EmployeeDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class EmployeeModel {
//
//    public boolean saveEmployee (EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
//
//        return CrudUtil.execute("insert into Employee values (?,?,?,?,?,?,?)",
//                employeeDto.getEmployeeId(),
//                employeeDto.getName(),
//                employeeDto.getAddress(),
//                employeeDto.getContact(),
//                employeeDto.getSalary(),
//                employeeDto.getHireDate(),
//                employeeDto.getRole()
//        );
//
//    }
//
//    public String getNextId() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select employee_id from Employee order by employee_id desc limit 1");
//
//        char tableChar = 'E';
//
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String lastIdNumberString = lastId.substring(1);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
//            return nextIdString;
//        }
//        return "E001";
//    }
//
//    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select * from Employee");
//
//        ArrayList<EmployeeDto> list = new ArrayList<>();
//        while (rst.next()) {
//            EmployeeDto employeeDto = new EmployeeDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getDouble(5),
//                    rst.getString(6),
//                    rst.getString(7)
//            );
//            list.add(employeeDto);
//        }
//        return list;
//    }
//
//    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("update Employee set name = ? , address = ?, contact = ?, salary = ?, hire_date = ?, role = ? where employee_id = ? ",
//                   employeeDto.getName(),
//                   employeeDto.getAddress(),
//                   employeeDto.getContact(),
//                employeeDto.getSalary(),
//                employeeDto.getHireDate(),
//                employeeDto.getRole(),
//                employeeDto.getEmployeeId()
//                );
//    }
//
//    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("delete from Employee where employee_id = ?", employeeId);
//    }
//
//    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select employee_id from Employee");
//        ArrayList<String> list = new ArrayList<>();
//        while (rst.next()){
//            String id = rst.getString(1);
//            list.add(id);
//        }
//        return list;
//    }
//}
