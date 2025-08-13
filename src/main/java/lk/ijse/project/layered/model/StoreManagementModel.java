//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.dto.StoreManagementDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class StoreManagementModel {
//    public boolean saveStore(StoreManagementDto storeManagementDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("insert into StoreManagement values (?,?,?)",
//                storeManagementDto.getStoreId(),
//                storeManagementDto.getOrderId(),
//                storeManagementDto.getCapacity()
//        );
//    }
//
//    public static String getNextId() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select store_id from StoreManagement order by store_id desc limit 1");
//        String tableString = "ST";
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String lastNumberString = lastId.substring(2);
//            int lastIdNumber = Integer.parseInt(lastNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            String nextIdString = String.format("%s%03d", tableString, nextIdNumber);
//            return nextIdString;
//        }
//        return "ST001";
//    }
//
//    public ArrayList<StoreManagementDto> getAllStore() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select * from StoreManagement ");
//        ArrayList<StoreManagementDto> list = new ArrayList<>();
//        while (rst.next()) {
//            StoreManagementDto storeManagementDto = new StoreManagementDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getDouble(3)
//            );
//            list.add(storeManagementDto);
//        }
//        return list;
//    }
//
//    public boolean updateStore(StoreManagementDto storeManagementDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("update StoreManagement set order_id = ?, capacity = ? where store_id = ?",
//                storeManagementDto.getOrderId(),
//                storeManagementDto.getCapacity(),
//                storeManagementDto.getStoreId()
//        );
//    }
//
//    public boolean deleteStore(String storeId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("delete from StoreManagement where store_id = ?", storeId);
//    }
//
//    public ArrayList<String> getAllStoreIds() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select store_id from StoreManagement");
//        ArrayList<String> list = new ArrayList<>();
//        while (rst.next()) {
//            String id = rst.getString(1);
//            list.add(id);
//        }
//        return list;
//    }
//
//    public boolean updateOrInsertStore(StoreManagementDto dto) throws SQLException, ClassNotFoundException {
//        boolean isUpdated = CrudUtil.execute("UPDATE StoreManagement SET order_id = ?, capacity = ? WHERE store_id = ?",
//                dto.getOrderId(), dto.getCapacity(), dto.getStoreId());
//
//        if (!isUpdated) {
//            return CrudUtil.execute("INSERT INTO StoreManagement (store_id, order_id, capacity) VALUES (?, ?, ?)",
//                    dto.getStoreId(), dto.getOrderId(), dto.getCapacity());
//        }
//        return true;
//    }
//
//
//    public String getStoreId(String orderId) throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("SELECT store_id FROM StoreManagement WHERE order_id = ?", orderId);
//        if (rst.next()) {
//            return rst.getString("store_id");
//        }
//        return "null";
//    }
//}
//
