package lk.ijse.project.layered.dao;

import lk.ijse.project.layered.dao.custom.QueryDAO;
import lk.ijse.project.layered.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance(){
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOType daoType){
        return switch (daoType){
            case CUSTOMER -> (T) new CustomerDAOImpl();
            case EMPLOYEE -> (T) new EmployeeDAOImpl();
            case INVENTORY -> (T) new InventoryDAOImpl();
            case ITEM -> (T) new ItemDAOImpl();
            case MACHINE -> (T) new MachineDAOImpl();
            case ORDER ->  (T) new OrderDAOImpl();
            case ORDER_ITEM ->  (T) new OrderItemDAOImpl();
            case ORDER_SERVICE ->   (T) new OrderServiceDAOImpl();
            case PAYMENT ->   (T) new PaymentDAOImpl();
            case PENALTY ->   (T) new PenaltyDAOImpl();
            case PROMOTION ->   (T) new PromotionDAOImpl();
            case SALARY ->   (T) new SalaryDAOImpl();
            case SERVICE ->   (T) new ServiceDAOImpl();
            case STORE_MANAGEMENT ->   (T) new StoreManagementDAOImpl();
            case  SUPPLIER ->  (T) new SupplierDAOImpl();
            case UTILITY_EXPENSE ->  (T) new UtilityExpenseDAOImpl();
            case QUERY -> (T) new QueryAOImpl();
        };
    }
}
