//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.dto.OrderItemDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class OrderItemModel {
// //   private final InventoryModel inventoryModel = new InventoryModel();
//    public boolean saveOrderDetailsList(ArrayList<OrderItemDto> cartList) throws SQLException, ClassNotFoundException {
//        for (OrderItemDto orderItemDto : cartList) {
//            boolean isDetailsSave = saveOrderDetails(orderItemDto);
//            if (!isDetailsSave) {
//                return false;
//            }
//            boolean isUpdated = inventoryModel.reduceQty(orderItemDto);
//            if (!isUpdated) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean saveOrderDetails(OrderItemDto orderItemDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("insert into OrderItem values (?,?,?,?)",
//                orderItemDto.getItemId(),
//                orderItemDto.getOrderId(),
//                orderItemDto.getQty(),
//                orderItemDto.getPrice()
//
//                );
//    }
//
//    public double getTotalItemCost(String orderId) throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("SELECT SUM(quantity * price) FROM OrderItem WHERE order_id = ?", orderId);
//        if (rst.next()) {
//            return rst.getDouble(1);
//        }
//        return 0.0;
//    }
//}
