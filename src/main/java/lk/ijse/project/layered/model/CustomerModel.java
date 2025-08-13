//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.dto.CustomerDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class CustomerModel {
//
//    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
//
//        return CrudUtil.execute(
//                "insert into Customer values (?,?,?,?,?)",
//                customerDto.getCustomerId(),
//                customerDto.getName(),
//                customerDto.getAddress(),
//                customerDto.getContact(),
//                customerDto.getEmail()
//        );
//
//    }
//    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "delete from Customer where customer_id = ? ", customerId
//        );
//    }
//
////    public String getNextId() throws SQLException, ClassNotFoundException {
////
////        ResultSet rst = CrudUtil.execute("select customer_id from Customer order by customer_id desc limit 1");
////
////        char tableChar = 'C';
////
////        if(rst.next()){
////            String lastId = rst.getString(1);
////            String lastIdNumberString = lastId.substring(1);
////            int lastIdNumber = Integer.parseInt(lastIdNumberString);
////            int nextIdNumber = lastIdNumber + 1;
////            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
////            return nextIdString;
////
////        }
////        return "C001";
////    }
//
//    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
//
//        ResultSet rst = CrudUtil.execute("select * from Customer");
//
//        ArrayList<CustomerDto> list = new ArrayList<>();
//        while(rst.next()){
//            CustomerDto customerDto =  new CustomerDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5)
//            );
//            list.add(customerDto);
//        }
//        return list;
//    }
//
//    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("update Customer set  name = ?, address = ?, contact = ?, email = ? where customer_id = ? ",
//                customerDto.getName(),
//                customerDto.getAddress(),
//                customerDto.getContact(),
//                customerDto.getEmail(),
//                customerDto.getCustomerId()
//
//                );
//    }
//
//    public ArrayList<String> getAllCustomersIds() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select customer_id from Customer");
//        ArrayList<String> list = new ArrayList<>();
//        while(rst.next()){
//           String id = rst.getString(1);
//           list.add(id);
//        }
//        return list;
//    }
//}
