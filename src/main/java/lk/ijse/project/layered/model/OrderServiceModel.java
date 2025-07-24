package lk.ijse.project.layered.model;

import lk.ijse.project.layered.dto.OrderServiceDto;
import lk.ijse.project.layered.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderServiceModel {

    public double getTotalServiceCost(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute(
                "SELECT SUM(s.price * o.Capacity) FROM OrderService os JOIN Service s ON os.service_id = s.service_id JOIN `order` o ON os.order_id = o.order_id WHERE os.order_id = ?",
                orderId
        );

        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0.0;

    }

    public boolean saveOrderService(OrderServiceDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO OrderService VALUES (?, ?)",
                 dto.getOrderId(),
                 dto.getServiceId()
        );
    }

    public boolean updateOrderService(OrderServiceDto orderServiceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update OrderService set service_id = ? where order_id =?",
                orderServiceDto.getServiceId(),
                orderServiceDto.getOrderId()
        );
    }
}
