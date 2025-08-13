//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.db.DBConnection;
//import lk.ijse.project.layered.dto.ItemDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class ItemModel {
//    //private final OrderItemModel orderItemModel = new OrderItemModel();
//
//
//    public boolean placeOrder(ItemDto itemDto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean isSave = CrudUtil.execute(
//                    "insert into Item values (?,?,?)",
//                    itemDto.getOrderId(),
//                    itemDto.getCustomerId(),
//                    itemDto.getDate()
//            );
//            if (isSave) {
//                boolean isDetailsSaved = orderItemModel.saveOrderDetailsList(itemDto.getCartList());
//                if (isDetailsSaved) {
//                    connection.commit();
//                    return true;
//                }
//            }
//            connection.rollback();
//            return false;
//
//        }catch (Exception e) {
//            connection.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            connection.setAutoCommit(true);
//        }
//
//    }
//}
//
