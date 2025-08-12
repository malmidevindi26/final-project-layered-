package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.ItemBO;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.*;
import lk.ijse.project.layered.db.DBConnection;
import lk.ijse.project.layered.dto.ItemDto;
import lk.ijse.project.layered.dto.OrderDto;
import lk.ijse.project.layered.dto.OrderItemDto;
import lk.ijse.project.layered.entity.InventoryEntity;
import lk.ijse.project.layered.entity.ItemEntity;
import lk.ijse.project.layered.entity.OrderEntity;
import lk.ijse.project.layered.entity.OrderItemEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ItemBOImpl implements ItemBO {
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);

    private OrderItemDAO orderItemDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER_ITEM);

    private InventoryDAO inventoryDAO = DAOFactory.getInstance().getDAO(DAOType.INVENTORY);

    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);

    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    @Override
//    public boolean placeOrder(ItemDto dto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        try {
//            connection.setAutoCommit(false);
//
//            Optional<OrderEntity> optionalOrder = orderDAO.findById(dto.getOrderId());
//            if (optionalOrder.isEmpty()) {
//                throw new NotFoundException("Order not found");
//            }
//
//            ItemEntity itemEntity = new ItemEntity();
//            itemEntity.setOrderId(dto.getOrderId());
//            itemEntity.setDate(String.valueOf(dto.getDate()));
//
//            boolean isOrderSave = itemDAO.save(itemEntity);
//            if (isOrderSave) {
//                boolean isSuccess = saveDetailsAndUpdateItem(dto.getCartList());
//                if (isSuccess) {
//                    connection.commit();
//                    return true;
//                }
//            }
//            connection.rollback();
//            return false;
//        } catch (Exception e) {
//            connection.rollback();
//            return false;
//        } finally {
//            connection.setAutoCommit(true);
//        }
//  }



    public boolean placeOrder(ItemDto dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            Optional<OrderEntity> optionalOrder = orderDAO.findById(dto.getOrderId());
            if (optionalOrder.isEmpty()) {
                System.out.println("Order not found with id: " + dto.getOrderId());
                throw new NotFoundException("Order not found");
            }

            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setOrderId(dto.getOrderId());
            itemEntity.setDate(String.valueOf(dto.getDate()));

            boolean isOrderSave = itemDAO.save(itemEntity);
            System.out.println("Saving order: " + isOrderSave);

            if (isOrderSave) {
                boolean isSuccess = saveDetailsAndUpdateItem(dto.getCartList());
                System.out.println("Saving order details and updating inventory: " + isSuccess);
                if (isSuccess) {
                    connection.commit();
                    System.out.println("Transaction committed.");
                    return true;
                }
            }
            connection.rollback();
            System.out.println("Transaction rolled back.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("Transaction rolled back due to exception.");
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }




    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = itemDAO.getLastId();
        String tableChar = "OR";
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }

    private boolean saveDetailsAndUpdateItem(List<OrderItemDto> detailsList) throws SQLException, ClassNotFoundException {
//        for (OrderItemDto detailsDTO : detailsList) {
//            OrderItemEntity orderItemEntity = new OrderItemEntity();
//            orderItemEntity.setItemId(detailsDTO.getItemId());
//            orderItemEntity.setOrderId(detailsDTO.getOrderId());
//            orderItemEntity.setQty(detailsDTO.getQty());
//            orderItemEntity.setPrice(detailsDTO.getPrice());
//
//            if (!orderItemDAO.save(orderItemEntity)) {
//                return false;
//            }
//
//            Optional<InventoryEntity> optionalInventory = inventoryDAO.findById(detailsDTO.getItemId());
//            if (optionalInventory.isEmpty()) {
//                throw new NotFoundException("Inventory not found");
//            }
//
//            boolean isInventoryUpdated = inventoryDAO.reduceQuantity(
//                    detailsDTO.getItemId(),
//                    (int) detailsDTO.getQty()
//            );
//            if (!isInventoryUpdated) {
//                return false;
//            }
//
//        }
//        return  true;

        for (OrderItemDto detailsDTO : detailsList) {
            System.out.println("Saving order item: " + detailsDTO.getItemId() + ", Qty: " + detailsDTO.getQty());

            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setItemId(detailsDTO.getItemId());
            orderItemEntity.setOrderId(detailsDTO.getOrderId());
            orderItemEntity.setQty(detailsDTO.getQty());
            orderItemEntity.setPrice(detailsDTO.getPrice());

            boolean saved = orderItemDAO.save(orderItemEntity);
            System.out.println("Order item save status: " + saved);
            if (!saved) {
                System.out.println("Failed to save order item: " + detailsDTO.getItemId());
                return false;
            }

            Optional<InventoryEntity> optionalInventory = inventoryDAO.findById(detailsDTO.getItemId());
            if (optionalInventory.isEmpty()) {
                System.out.println("Inventory not found for item: " + detailsDTO.getItemId());
                throw new NotFoundException("Inventory not found");
            }

            boolean isInventoryUpdated = inventoryDAO.reduceQuantity(detailsDTO.getItemId(), (int) detailsDTO.getQty());
            System.out.println("Inventory update status for item " + detailsDTO.getItemId() + ": " + isInventoryUpdated);
            if (!isInventoryUpdated) {
                System.out.println("Failed to update inventory for item: " + detailsDTO.getItemId());
                return false;
            }
        }
        return true;

    }
}
