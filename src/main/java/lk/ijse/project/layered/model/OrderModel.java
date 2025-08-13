//package lk.ijse.project.layered.model;
//
//import lk.ijse.project.layered.db.DBConnection;
//import lk.ijse.project.layered.dto.OrderDto;
//import lk.ijse.project.layered.util.CrudUtil;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class OrderModel {
//    public static List<String> getOrderIds() throws SQLException, ClassNotFoundException {
//        Connection con = DBConnection.getInstance().getConnection();
//        String sql = "SELECT order_id FROM `order`";
//        PreparedStatement pstm = con.prepareStatement(sql);
//        ResultSet rs = pstm.executeQuery();
//
//        List<String> ids = new ArrayList<>();
//        while (rs.next()) {
//            ids.add(rs.getString("order_id"));
//        }
//        return ids;
//    }
//
//    public static boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("delete from `order` where order_id = ? " , orderId);
//    }
//
//    public boolean saveOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("insert into `order` values (?,?,?,?,?,?,?)",
//              orderDto.getOrderId(),
//              orderDto.getCustomId(),
//              orderDto.getClothCategory(),
//              orderDto.getDate(),
//              orderDto.getStatus(),
//              orderDto.getServiceId(),
//              orderDto.getCapacity()
//        );
//    }
//
//    public String getNextId() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select order_id from `order` order by order_id desc limit 1");
//        char tableChar = 'O';
//
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String lasstIdNumberString = lastId.substring(1);
//            int lastIdNumber = Integer.parseInt(lasstIdNumberString);
//            int nextIdNumber = lastIdNumber + 1;
//            String nextIdString  = String.format("%s%03d", tableChar, nextIdNumber);
//            return nextIdString;
//        }
//        return "O001";
//    }
//
//    public ArrayList<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select * from `order`");
//
//        ArrayList <OrderDto> list = new ArrayList<>();
//        while (rst.next()) {
//            OrderDto orderDto = new OrderDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getString(6),
//                    rst.getDouble(7)
//            );
//            list.add(orderDto);
//        }
//        return list;
//    }
//
//    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select order_id from `order`");
//        ArrayList<String> list = new ArrayList<>();
//        while (rst.next()) {
//            String id = rst.getString(1);
//            list.add(id);
//        }
//        return list;
//    }
//
//    public boolean updateOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("update `order` set customer_id = ?, clothing_category = ?, order_date = ?, status = ?, service_id = ?,Capacity = ? where order_id = ?",
//                orderDto.getCustomId(),
//                orderDto.getClothCategory(),
//                orderDto.getDate(),
//                orderDto.getStatus(),
//                orderDto.getServiceId(),
//                orderDto.getCapacity(),
//                orderDto.getOrderId()
//        );
//    }
//
//
//    public OrderDto findById(String selectedItemId) throws SQLException, ClassNotFoundException {
//
//        Connection con = DBConnection.getInstance().getConnection();
//        String sql = "SELECT customer_id, clothing_category FROM `order` WHERE order_id = ?";
//        PreparedStatement pstm = con.prepareStatement(sql);
//        pstm.setString(1, selectedItemId);
//        ResultSet rs = pstm.executeQuery();
//
//        if (rs.next()) {
//            return new OrderDto(
//                    selectedItemId,
//                    rs.getString("customer_id"),
//                    rs.getString("clothing_category")
//            );
//        }
//        return null;
//    }
//
//
//
//
//}
//
//
//
//
