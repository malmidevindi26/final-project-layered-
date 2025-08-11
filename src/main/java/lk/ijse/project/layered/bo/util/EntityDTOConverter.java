package lk.ijse.project.layered.bo.util;

import lk.ijse.project.layered.bo.custom.CustomerBO;
import lk.ijse.project.layered.dto.*;
import lk.ijse.project.layered.entity.*;

public class EntityDTOConverter {
    public CustomerDto getCustomerDTO(CustomerEntity customerEntity) {
        CustomerDto dto = new CustomerDto();
        dto.setCustomerId(customerEntity.getId());
        dto.setName(customerEntity.getName());
        dto.setAddress(customerEntity.getAddress());
        dto.setContact(customerEntity.getPhone());
        dto.setEmail(customerEntity.getEmail());
        return dto;
    }
    public CustomerEntity getCustomer(CustomerDto dto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(dto.getCustomerId());
        customerEntity.setName(dto.getName());
        customerEntity.setAddress(dto.getAddress());
        customerEntity.setPhone(dto.getContact());
        customerEntity.setEmail(dto.getEmail());
        return customerEntity;
    }
    public EmployeeDto getEmployeeDTO(EmployeeEntity employeeEntity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(employeeEntity.getId());
        dto.setName(employeeEntity.getName());
        dto.setAddress(employeeEntity.getAddress());
        dto.setContact(employeeEntity.getPhone());
        dto.setSalary(employeeEntity.getSalary());
        dto.setHireDate(String.valueOf(employeeEntity.getDate()));
        dto.setRole(employeeEntity.getRole());
        return dto;
    }
    public EmployeeEntity getEmployee(EmployeeDto dto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(dto.getEmployeeId());
        employeeEntity.setName(dto.getName());
        employeeEntity.setAddress(dto.getAddress());
        employeeEntity.setPhone(dto.getContact());
        employeeEntity.setSalary(dto.getSalary());
        employeeEntity.setDate(dto.getHireDate());
        employeeEntity.setRole(dto.getRole());
        return employeeEntity;
    }
    public InventoryDto getInventoryDTO(InventoryEntity inventoryEntity) {
        InventoryDto dto = new InventoryDto();
        dto.setItemId(inventoryEntity.getId());
        dto.setName(inventoryEntity.getName());
        dto.setManufacturerdDate(String.valueOf(inventoryEntity.getManuDate()));
        dto.setExpaireDate(String.valueOf(inventoryEntity.getExpiryDate()));
        dto.setStatus(inventoryEntity.getStatus());
        dto.setQuantity(inventoryEntity.getQty());
        dto.setPrice(inventoryEntity.getPrice());
        return dto;
    }
    public InventoryEntity getInventory(InventoryDto dto) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(dto.getItemId());
        inventoryEntity.setName(dto.getName());
        inventoryEntity.setManuDate(dto.getManufacturerdDate());
        inventoryEntity.setExpiryDate(dto.getExpaireDate());
        inventoryEntity.setStatus(dto.getStatus());
        inventoryEntity.setQty(dto.getQuantity());
        inventoryEntity.setPrice(dto.getPrice());
        return inventoryEntity;
    }

    public MachineDto getMachineDTO(MachineEntity machineEntity) {
        MachineDto dto = new MachineDto();
        dto.setMachineId(machineEntity.getId());
        dto.setType(machineEntity.getType());
        dto.setStatus(machineEntity.getStatus());
        dto.setCost(machineEntity.getCost());
        dto.setDate(machineEntity.getDate());
        return dto;
    }
    public MachineEntity getMachine(MachineDto dto) {
        MachineEntity machineEntity = new MachineEntity();
        machineEntity.setId(dto.getMachineId());
        machineEntity.setType(dto.getType());
        machineEntity.setStatus(dto.getStatus());
        machineEntity.setCost(dto.getCost());
        machineEntity.setDate(dto.getDate());
        return machineEntity;
    }
    public OrderDto getOrderDTO(OrderEntity orderEntity) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(orderEntity.getOrderId());
        dto.setCustomId(orderEntity.getCustomerId());
        dto.setClothCategory(orderEntity.getCategory());
        dto.setDate(orderEntity.getDate());
        dto.setStatus(orderEntity.getStatus());
        dto.setServiceId(orderEntity.getServiceId());
        dto.setCapacity(orderEntity.getCapacity());
        return dto;
    }
    public OrderEntity getOrder(OrderDto dto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(dto.getOrderId());
        orderEntity.setCustomerId(dto.getCustomId());
        orderEntity.setCategory(dto.getClothCategory());
        orderEntity.setDate(dto.getDate());
        orderEntity.setStatus(dto.getStatus());
        orderEntity.setServiceId(dto.getServiceId());
        orderEntity.setCapacity(dto.getCapacity());
        return orderEntity;
    }
    public PaymentDto getPaymentDTO(PaymentEntity paymentEntity) {
        PaymentDto dto = new PaymentDto();
        dto.setPaymentId(paymentEntity.getPaymentId());
        dto.setOrderId(paymentEntity.getOrderId());
        dto.setPromotionAmount(paymentEntity.getPromotionAmount());
        dto.setPenaltyAmount(paymentEntity.getPenaltyAmount());
        dto.setAmount(paymentEntity.getTotalAmount());
        dto.setMethod(paymentEntity.getMethod());
        dto.setStatus(paymentEntity.getStatus());
        dto.setDate(paymentEntity.getDate());
        return dto;
    }
    public PaymentEntity getPayment(PaymentDto dto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentId(dto.getPaymentId());
        paymentEntity.setOrderId(dto.getOrderId());
        paymentEntity.setPromotionAmount(dto.getPromotionAmount());
        paymentEntity.setPenaltyAmount(dto.getPenaltyAmount());
        paymentEntity.setTotalAmount(dto.getAmount());
        paymentEntity.setMethod(dto.getMethod());
        paymentEntity.setStatus(dto.getStatus());
        paymentEntity.setDate(dto.getDate());
        return paymentEntity;
    }
    public PenaltyDto getPenaltyDTO(PenaltyEntity penaltyEntity) {
        PenaltyDto dto = new PenaltyDto();
        dto.setPenaltyId(penaltyEntity.getPenaltyId());
        dto.setOrderId(penaltyEntity.getOrderId());
        dto.setStoreId(penaltyEntity.getStoreId());
        dto.setAmount(penaltyEntity.getAmount());
        dto.setReason(penaltyEntity.getReason());
        return dto;
    }
    public PenaltyEntity getPenalty(PenaltyDto dto) {
        PenaltyEntity penaltyEntity = new PenaltyEntity();
        penaltyEntity.setPenaltyId(dto.getPenaltyId());
        penaltyEntity.setOrderId(dto.getOrderId());
        penaltyEntity.setStoreId(dto.getStoreId());
        penaltyEntity.setAmount(dto.getAmount());
        penaltyEntity.setReason(dto.getReason());
        return penaltyEntity;
    }
    public PromotionDto getPromotionDTO(PromotionEntity promotionEntity) {
        PromotionDto dto = new PromotionDto();
        dto.setPromotionId(promotionEntity.getId());
        dto.setCode(promotionEntity.getCode());
        dto.setDiscount(promotionEntity.getDiscountPercent());
        dto.setDate(promotionEntity.getExpirationDate());
        dto.setDescription(promotionEntity.getDescription());
        return dto;
    }
    public PromotionEntity getPromotion(PromotionDto dto) {
        PromotionEntity promotionEntity = new PromotionEntity();
        promotionEntity.setId(dto.getPromotionId());
        promotionEntity.setCode(dto.getCode());
        promotionEntity.setDiscountPercent(dto.getDiscount());
        promotionEntity.setExpirationDate(dto.getDate());
        promotionEntity.setDescription(dto.getDescription());
        return promotionEntity;
    }
    public SalaryDto  getSalaryDTO(SalaryEntity salaryEntity) {
        SalaryDto dto = new SalaryDto();
        dto.setSalaryId(salaryEntity.getSalaryId());
        dto.setEmployeeId(salaryEntity.getEmployeeId());
        dto.setAmount(salaryEntity.getSalary());
        dto.setDate(salaryEntity.getIssuedDate());
        return dto;
    }
    public SalaryEntity getSalary(SalaryDto dto) {
        SalaryEntity salaryEntity = new SalaryEntity();
        salaryEntity.setSalaryId(dto.getSalaryId());
        salaryEntity.setEmployeeId(dto.getEmployeeId());
        salaryEntity.setSalary(dto.getAmount());
        salaryEntity.setIssuedDate(dto.getDate());
        return salaryEntity;
    }
    public ServiceDto  getServiceDTO(ServiceEntity serviceEntity) {
        ServiceDto dto = new ServiceDto();
        dto.setServiceId(serviceEntity.getId());
        dto.setName(serviceEntity.getName());
        dto.setPrice(serviceEntity.getPrice());
        dto.setDescription(serviceEntity.getDescription());
        return dto;
    }
    public ServiceEntity getService(ServiceDto dto) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(dto.getServiceId());
        serviceEntity.setName(dto.getName());
        serviceEntity.setPrice(dto.getPrice());
        serviceEntity.setDescription(dto.getDescription());
        return serviceEntity;
    }
    public StoreManagementDto getStoreManagementDTO(StoreManagementEntity storeManagementEntity) {
        StoreManagementDto dto = new StoreManagementDto();
        dto.setStoreId(storeManagementEntity.getStoreId());
        dto.setOrderId(storeManagementEntity.getOrderId());
        dto.setCapacity(storeManagementEntity.getCapacity());
        return dto;
    }
    public StoreManagementEntity getStoreManagement(StoreManagementDto dto) {
        StoreManagementEntity storeManagementEntity = new StoreManagementEntity();
        storeManagementEntity.setStoreId(dto.getStoreId());
        storeManagementEntity.setOrderId(dto.getOrderId());
        storeManagementEntity.setCapacity(dto.getCapacity());
        return storeManagementEntity;
    }
    public SupplierDto getSupplierDTO(SupplierEntity supplierEntity) {
        SupplierDto dto = new SupplierDto();
        dto.setSupplierId(supplierEntity.getSupplierId());
        dto.setItemId(supplierEntity.getItemId());
        dto.setName(supplierEntity.getName());
        dto.setAddress(supplierEntity.getAddress());
        dto.setContact(supplierEntity.getContact());
        dto.setAmount(supplierEntity.getAmount());
        dto.setDate(supplierEntity.getDate());
        return dto;
    }
    public SupplierEntity getSupplier(SupplierDto dto) {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setSupplierId(dto.getSupplierId());
        supplierEntity.setItemId(dto.getItemId());
        supplierEntity.setName(dto.getName());
        supplierEntity.setAddress(dto.getAddress());
        supplierEntity.setContact(dto.getContact());
        supplierEntity.setAmount(dto.getAmount());
        supplierEntity.setDate(dto.getDate());
        return supplierEntity;
    }
    public UtilityExpenseDto getUtilityExpenseDTO(UtilityExpenseEntity expenseEntity) {
        UtilityExpenseDto dto = new UtilityExpenseDto();
        dto.setExpenseId(expenseEntity.getId());
        dto.setExpenseType(expenseEntity.getType());
        dto.setAmount(expenseEntity.getAmount());
        dto.setBillingDate(expenseEntity.getDate());
        return dto;
    }
    public UtilityExpenseEntity getUtilityExpense(UtilityExpenseDto dto) {
        UtilityExpenseEntity expenseEntity = new UtilityExpenseEntity();
        expenseEntity.setId(dto.getExpenseId());
        expenseEntity.setType(dto.getExpenseType());
        expenseEntity.setAmount(dto.getAmount());
        expenseEntity.setDate(dto.getBillingDate());
        return expenseEntity;
    }
}
