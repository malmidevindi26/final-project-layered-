package lk.ijse.project.layered.bo.custom;

import lk.ijse.project.layered.bo.SuperBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dao.custom.CustomerDAO;
import lk.ijse.project.layered.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
   List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

   void saveCustomer(CustomerDto dto) throws Exception, DuplicateException;

   void updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

   boolean deleteCustomer(String id) throws Exception, InUseException;

   String getNextId() throws Exception;
}
