package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.OrderBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.OrderDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.OrderDto;
import lk.ijse.project.layered.entity.CustomerEntity;
import lk.ijse.project.layered.entity.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderBOImpl implements OrderBO {
    private final EntityDTOConverter converter = new EntityDTOConverter();
    private final OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    @Override
    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderEntity> orderEntities = orderDAO.getAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            orderDtos.add(converter.getOrderDTO(orderEntity));

        }
        return orderDtos;
    }

    @Override
    public void saveOrder(OrderDto dto) throws Exception, DuplicateException {

        Optional<OrderEntity> optionalOrder = orderDAO.findById(dto.getOrderId());
        if (optionalOrder.isPresent()) {
            throw new DuplicateException("Duplicate order id");
        }

        OrderEntity orderEntity = converter.getOrder(dto);

        boolean save = orderDAO.save(orderEntity);
    }

    @Override
    public void updateOrder(OrderDto dto) throws SQLException, ClassNotFoundException {

        Optional<OrderEntity> optionalOrder = orderDAO.findById(dto.getOrderId());
        if(optionalOrder.isEmpty()){
            throw new NotFoundException("order not found");
        }

        OrderEntity orderEntity = converter.getOrder(dto);
        orderDAO.update(orderEntity);
    }

    @Override
    public boolean deleteOrder(String id) throws Exception, InUseException {
        Optional<OrderEntity> optionalOrder = orderDAO.findById(id);
        if(optionalOrder.isEmpty()){
            throw new NotFoundException("Order not found");
        }

        try {
            boolean delete = orderDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        String lastId = orderDAO.getLastId();
        char tableChar = 'O';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
