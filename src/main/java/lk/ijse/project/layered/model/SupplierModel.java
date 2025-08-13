//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.dto.SupplierDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class SupplierModel {
//    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("insert into Supplier values (?,?,?,?,?,?,?) ",
//            supplierDto.getSupplierId(),
//            supplierDto.getItemId(),
//            supplierDto.getName(),
//            supplierDto.getAddress(),
//            supplierDto.getContact(),
//            supplierDto.getAmount(),
//            supplierDto.getDate()
//     );
//    }
//
//    public String getNextId() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select supplier_id from Supplier order by supplier_id desc limit 1" );
//        String tableString = "SP";
//        while (rst.next()) {
//            String lastId = rst.getString(1);
//            String lastNumberStrinf = lastId.substring(2);
//            int lastIdNumber = Integer.parseInt(lastNumberStrinf);
//            int nextIdNumber = lastIdNumber + 1;
//            String nextIdString = String.format(tableString +"%03d",nextIdNumber);
//            return nextIdString;
//        }
//        return "SP001";
//    }
//
//    public ArrayList<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select * from Supplier");
//        ArrayList<SupplierDto> list = new ArrayList<>();
//        while (rst.next()) {
//            SupplierDto supplierDto = new SupplierDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getDouble(6),
//                    rst.getString(7)
//            );
//            list.add(supplierDto);
//        }
//        return list;
//    }
//
//    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("update Supplier set item_id = ?, name = ?, address = ?, contact = ?, amount = ?, date = ? where supplier_id = ?",
//               supplierDto.getItemId(),
//               supplierDto.getName(),
//               supplierDto.getAddress(),
//               supplierDto.getContact(),
//               supplierDto.getAmount(),
//               supplierDto.getDate(),
//               supplierDto.getSupplierId()
//        );
//    }
//
//    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("delete from Supplier where supplier_id = ? ",supplierId);
//    }
//}
