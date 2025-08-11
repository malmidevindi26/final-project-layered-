package lk.ijse.project.layered.bo;

import lk.ijse.project.layered.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }
    public static BOFactory getInstance(){
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public <H extends SuperBO> H getBO(BOType boType) {
        return switch (boType) {
            case CUSTOMER -> (H) new CustomerBOImpl();
            case EMPLOYEE -> (H) new EmployeeBOImpl();
            case INVENTORY ->  (H) new InventoryBOImpl();
            case ITEM ->  (H) new ItemBOImpl();
            case MACHINE ->  (H) new MachineBOImpl();
            case ORDER ->  (H) new OrderBOImpl();
            case PAYMENT ->  (H) new PaymentBOImpl();
            case PENALTY ->  (H) new PenaltyBOImpl();
            case PROMOTION ->  (H) new PromotionBOImpl();
            case SALARY ->  (H) new SalaryBOImpl();
            case SERVICE ->  (H) new ServiceBOImpl();
            case STOREMANAGEMENT -> (H)  new StoreManagementBOImpl();
            case SUPPLIER ->  (H) new SupplierBOImpl();
            case UTILITYEXPENSE -> (H) new UtilityExpenseBOImpl();
        };
    }

}
