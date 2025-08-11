package lk.ijse.project.layered.bo.custom.impl;

import lk.ijse.project.layered.bo.custom.CustomerBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.bo.exception.NotFoundException;
import lk.ijse.project.layered.bo.util.EntityDTOConverter;
import lk.ijse.project.layered.dao.DAOFactory;
import lk.ijse.project.layered.dao.DAOType;
import lk.ijse.project.layered.dao.custom.CustomerDAO;
import lk.ijse.project.layered.dao.custom.OrderDAO;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.entity.CustomerEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    private final OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerEntity> customerEntities = customerDAO.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities) {
            customerDtos.add(converter.getCustomerDTO(customerEntity));

        }
        return customerDtos;
    }

    @Override
    public void saveCustomer(CustomerDto dto) throws Exception, DuplicateException {
        Optional<CustomerEntity> optionalCustomer = customerDAO.findById(dto.getCustomerId());
        if (optionalCustomer.isPresent()) {
            throw new DuplicateException("Duplicate customer id");
        }

        CustomerEntity customerEntity = converter.getCustomer(dto);

        boolean save = customerDAO.save(customerEntity);

    }

    @Override
    public void updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
         Optional<CustomerEntity> optionalCustomer = customerDAO.findById(dto.getCustomerId());
         if(optionalCustomer.isEmpty()){
             throw new NotFoundException("Customer not found");
         }

         CustomerEntity customerEntity = converter.getCustomer(dto);
         customerDAO.update(customerEntity);
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception, InUseException {
        Optional<CustomerEntity> optionalCustomer = customerDAO.findById(id);
        if(optionalCustomer.isEmpty()){
            throw new NotFoundException("Customer not found");
        }
        if(orderDAO.existOrdersByCustomerId(id)){
            throw new InUseException("Customer has orders");
        }
        try {
            boolean delete = customerDAO.delete(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String getNextId() throws Exception {
       String lastId = customerDAO.getLastId();
        char tableChar = 'C';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }
}
