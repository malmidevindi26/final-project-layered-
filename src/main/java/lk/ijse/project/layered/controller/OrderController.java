package lk.ijse.project.layered.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.layered.bo.BOFactory;
import lk.ijse.project.layered.bo.BOType;
import lk.ijse.project.layered.bo.custom.OrderBO;
import lk.ijse.project.layered.db.DBConnection;
import lk.ijse.project.layered.dto.OrderDto;
import lk.ijse.project.layered.dto.OrderServiceDto;
import lk.ijse.project.layered.dto.StoreManagementDto;
import lk.ijse.project.layered.dto.tm.OrderTM;
import lk.ijse.project.layered.model.*;
//import lk.ijse.project.model.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class OrderController implements Initializable {
    public Label lblOrderId;
    public TextField txtCustomId;
    public TextField txtCate;
    public TextField txtDate;
    public TextField txtStatus;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ServiceModel serviceModel = new ServiceModel();
    private  final StoreManagementModel storeManagementModel = new StoreManagementModel();
    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);

    public TableView <OrderTM> tblOrder;
    public TableColumn <OrderTM, String> colOrId;
    public TableColumn <OrderTM, String> colCustomId;
    public TableColumn <OrderTM, String> colCate;
    public TableColumn <OrderTM, String> colDate;
    public TableColumn <OrderTM, String> colStatus;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;
    public ComboBox <String>comCustId;
    public ComboBox <String> comStatus;
    public ComboBox <String> comServiceId;
    public TableColumn <OrderTM, String> colService;
    public TextField txtCapacity;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String capacityPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtCate.setStyle("-fx-text-fill: black;");
        txtCapacity.setStyle("-fx-text-fill: black;");
        txtDate.setStyle("-fx-text-fill: black;");

        if (!txtCapacity.getText().matches(capacityPattern)) {
            txtCapacity.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtCate.getText().matches(namePattern)) {
            txtCate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtDate.getText().matches(datePattern)) {
            txtDate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comStatus.getItems().addAll("Processing", "Completed","Picked Up", "Cancelled");

        colOrId.setText("Order Id");
        colCustomId.setText("Custom Id");
        colCate.setText("Category");
        colDate.setText("Date");
        colStatus.setText("Status");
        colService.setText("ServiceId");

        colOrId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCate.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colService.setCellValueFactory(new PropertyValueFactory<>("serviceId"));


        try {
           resetPage();
           loadCustomerIds();
           loadServiceIds();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to load data").show();
            e.printStackTrace();
        }

    }

    private void lodaTableData() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDto> orderDtoArrayList = orderModel.getAllOrders();

        ObservableList<OrderTM> list = FXCollections.observableArrayList();
        for (OrderDto orderDto : orderDtoArrayList) {
            OrderTM orderTM = new OrderTM(
                    orderDto.getOrderId(),orderDto.getCustomId(),orderDto.getClothCategory(),
                    orderDto.getDate(),orderDto.getStatus(),orderDto.getServiceId()
            );
            list.add(orderTM);
        }
        tblOrder.setItems(list);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (validateInput()) {
            String orderId = lblOrderId.getText();
            String customerId = comCustId.getValue();
            String cate = txtCate.getText();
            String date = txtDate.getText();
            String status = comStatus.getValue();
            String serviceId = comServiceId.getValue();
            double capacity = Double.parseDouble(txtCapacity.getText());


            OrderDto orderDto = new OrderDto(orderId, customerId, cate, date, status, serviceId, capacity);


            try {
                boolean isSave = orderModel.saveOrder(orderDto);
                if (isSave) {
                    OrderServiceDto orderServiceDto = new OrderServiceDto( orderId, serviceId);
                    boolean isOrderServiceSave = new OrderServiceModel().saveOrderService(orderServiceDto);

                    if (isOrderServiceSave) {
                        String storeId = StoreManagementModel.getNextId();
                        StoreManagementDto storeDto = new StoreManagementDto(storeId,orderId,capacity);
                        boolean isStoreSave = storeManagementModel.saveStore(storeDto);
                        if (isStoreSave) {
                            resetPage();
                            new Alert(Alert.AlertType.INFORMATION, "Order , store and Order Service Saved").show();
                        }else {
                            new Alert(Alert.AlertType.ERROR, "Order and Service saved, but failed to save Store").show();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Order saved but failed to save Order Service").show();
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR, "Order Not Save").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Order Not Save").show();
                e.printStackTrace();
            }
        }

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
       // String nextId = orderModel.getNextId();
        String nextId = orderBO.getNextId();
        lblOrderId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String orderId = lblOrderId.getText();
           String customerId = comCustId.getValue();
           String cate = txtCate.getText();
           String date = txtDate.getText();
           String status = comStatus.getValue();
           String serviceId = comServiceId.getValue();
           double capacity = Double.parseDouble(txtCapacity.getText());


           OrderDto orderDto = new OrderDto(orderId, customerId, cate, date, status, serviceId, capacity);


           try {
               boolean isUpdate = orderModel.updateOrder(orderDto);
               if (isUpdate) {
                   OrderServiceDto orderServiceDto = new OrderServiceDto( orderId, serviceId);
                   boolean isOrderServiceSave = new OrderServiceModel().updateOrderService(orderServiceDto);

                   if (isOrderServiceSave) {
                       String storeId = StoreManagementModel.getNextId();
                       StoreManagementDto storeDto = new StoreManagementDto(storeId,orderId,capacity);
                       boolean isStoreUpdate = storeManagementModel.updateOrInsertStore(storeDto);
                       if (isStoreUpdate) {
                           resetPage();
                           new Alert(Alert.AlertType.INFORMATION, "Order , store and Order Service update").show();
                       }else {
                           new Alert(Alert.AlertType.ERROR, "Order and Service update, but failed to save Store").show();
                       }
                   }else {
                       new Alert(Alert.AlertType.ERROR, "Order update but failed to save Order Service").show();
                   }

               }else {
                   new Alert(Alert.AlertType.ERROR, "Order Not update").show();
               }
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR,"Order Not update").show();
               e.printStackTrace();
           }
       }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this order?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String orderId = lblOrderId.getText();
               // boolean isDelete = OrderModel.deleteOrder(orderId);
                boolean isDelete = orderBO.deleteOrder(orderId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Order Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Order Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Order").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        OrderTM selectedItem = tblOrder.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblOrderId.setText(selectedItem.getOrderId());
            comCustId.setValue(selectedItem.getCustomerId());
            txtCate.setText(selectedItem.getCategory());
            txtDate.setText(selectedItem.getDate());
            comStatus.setValue(selectedItem.getStatus());
            comServiceId.setValue(selectedItem.getServiceId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
        }

    }
    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        lodaTableData();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        btnReport.setDisable(true);

        comCustId.setValue("");
        txtCate.setText("");
        txtDate.setText("");
        comStatus.setValue("");
        comServiceId.setValue("");
        txtCapacity.setText("");
    }

    private void loadCustomerIds() throws ClassNotFoundException, SQLException {
        ArrayList<String> list = customerModel.getAllCustomersIds();
        ObservableList<String>  customerIds = FXCollections.observableArrayList();
        customerIds.addAll(list);
        comCustId.setItems(customerIds);

    }

    private void loadServiceIds() throws ClassNotFoundException, SQLException {
        ArrayList<String> list = serviceModel.getAllServiceIds();
        ObservableList<String> serviceIds = FXCollections.observableArrayList();
        serviceIds.addAll(list);
        comServiceId.setItems(serviceIds);
    }


    public void ReportOnAction(ActionEvent actionEvent) {
        OrderTM selectedItem = tblOrder.getSelectionModel().getSelectedItem();
        if (orderModel == null) {
            return;
        }
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/Report/order.jrxml")
            );
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameres = new HashMap<>();
            parameres.put("p_order_id", selectedItem.getOrderId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameres,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
