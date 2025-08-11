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
import lk.ijse.project.layered.bo.custom.PaymentBO;
import lk.ijse.project.layered.bo.custom.PenaltyBO;
import lk.ijse.project.layered.bo.custom.StoreManagementBO;
import lk.ijse.project.layered.dto.PaymentDto;
import lk.ijse.project.layered.dto.PenaltyDto;
import lk.ijse.project.layered.dto.tm.PaymentTM;
import lk.ijse.project.layered.model.*;
//import lk.ijse.project.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public Label lblPayId;
    public TextField txtOrderId;
    public TextField txtProId;
    public TextField txtPenaltyId;
    public TextField txtAmount;
    public TextField txtMethod;
    public TextField txtStatus;
    public TextField txtDate;
    private final PaymentModel paymentModel = new PaymentModel();
    private final OrderModel orderModel = new OrderModel();
    private final OrderItemModel orderItemModel = new OrderItemModel();
    private final OrderServiceModel orderServiceModel = new OrderServiceModel();
    private final PenaltyModel penaltyModel = new PenaltyModel();
    private final StoreManagementModel storeModel = new StoreManagementModel();
    private final PaymentBO paymentBO = BOFactory.getInstance().getBO(BOType.PAYMENT);
    private final PenaltyBO penaltyBO = BOFactory.getInstance().getBO(BOType.ORDER);
    private final StoreManagementBO storeManagementBO = BOFactory.getInstance().getBO(BOType.STOREMANAGEMENT);

  //  private String penaltyId;

    public TableView <PaymentTM> tblPayment;
    public TableColumn <PaymentTM, String> colPayId;
    public TableColumn <PaymentTM, String> colOrderId;
    public TableColumn <PaymentTM, String> colProAmount;
    public TableColumn <PaymentTM, String> colPenaltyAmount;
    public TableColumn <PaymentTM, Double> colTotal;
    public TableColumn <PaymentTM, String> colMethod;
    public TableColumn <PaymentTM, String> colStatus;
    public TableColumn <PaymentTM, String> colDate;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public ComboBox <String> cmOrderId;
    public Label lblService;
    public Label lblItem;
    public ComboBox <String> comMethod;
    public ComboBox <String>comStatu;

    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtAmount.setStyle("-fx-text-fill: black;");
        txtDate.setStyle("-fx-text-fill: black;");

        if (!txtAmount.getText().matches(amountPattern)) {
            txtAmount.setStyle("-fx-text-fill: red;");
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

        comMethod.getItems().addAll("Cash", "Card");
        comStatu.getItems().addAll("Done", "Inactive");

        ///
        txtProId.textProperty().addListener((observable, oldVal, newVal) -> updateTotalAmount());
        txtPenaltyId.textProperty().addListener((observable, oldVal, newVal) -> updateTotalAmount());
        txtDate.textProperty().addListener((observable, oldVal, newVal) -> updateTotalAmount());
        cmOrderId.valueProperty().addListener((observable, oldVal, newVal) -> updateTotalAmount());

        ///
        colPayId.setText("Payment Id");
        colOrderId.setText("Order Id");
        colProAmount.setText("Promotion amount");
        colPenaltyAmount.setText("Penalty amount");
        colTotal.setText("Total");
        colMethod.setText("Method");
        colStatus.setText("Status");
        colDate.setText("Date");

        colPayId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProAmount.setCellValueFactory(new PropertyValueFactory<>("promotionAmount"));
        colPenaltyAmount.setCellValueFactory(new PropertyValueFactory<>("penaltyAmount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            resetPage();
            loadOrderIds();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Failed to load data").show();
            e.printStackTrace();
        }
    }
    /// /
    private void updateTotalAmount() {
        try {
            OrderItemModel orderItemModel = new OrderItemModel();
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            PenaltyModel penaltyModel = new PenaltyModel();

            String orderId = cmOrderId.getValue();
            String paymentDate = txtDate.getText();

            if (orderId == null || orderId.isEmpty() || paymentDate == null || paymentDate.isEmpty()) return;

            double itemCost = orderItemModel.getTotalItemCost(orderId);
            lblItem.setText(String.format("%.2f", itemCost));
            double serviceCost = orderServiceModel.getTotalServiceCost(orderId);
            lblService.setText(String.format("%.2f",serviceCost));
            double penalty = penaltyModel.getPenaltyAmount(orderId, paymentDate);
            txtPenaltyId.setText(String.format("%.2f", penalty));
            double promotion = txtProId.getText().isEmpty() ? 0 : Double.parseDouble(txtProId.getText());

            double totalAmount = itemCost + serviceCost + penalty - promotion;

            txtAmount.setText(String.format("%.2f", totalAmount));

        } catch (Exception e) {
            e.printStackTrace();
            txtAmount.setText("0.00");
        }
    }

    /// /

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList <PaymentDto> paymentDtoArrayList = paymentModel.getAllPayment();
        ObservableList <PaymentTM> list = FXCollections.observableArrayList();

        for (PaymentDto paymentDto : paymentDtoArrayList) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDto.getPaymentId(),paymentDto.getOrderId(),paymentDto.getPromotionAmount(),
                    paymentDto.getPenaltyAmount(),paymentDto.getAmount(),paymentDto.getMethod(),
                    paymentDto.getStatus(),paymentDto.getDate()
            );
            list.add(paymentTM);
        }
        tblPayment.setItems(list);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (validateInput()) {
            /// /
            updateTotalAmount();
            ///

            String paymentId = lblPayId.getText();
            String orderId = cmOrderId.getValue();
            double promotionAmount = Double.parseDouble(txtProId.getText());
            double penaltyMount = Double.parseDouble(txtPenaltyId.getText());
            double amount = Double.parseDouble(txtAmount.getText());
            String method = comMethod.getValue();
            String status = comStatu.getValue();
            String date = txtDate.getText();

           // penaltyId = penaltyModel.getNextId();
            try {
               String penaltyId = penaltyBO.getNextId();
        ///////////////////////////////////////////////////////////////////
                String storeId = storeModel.getStoreId(orderId);
        ///////////////////////////////////////////////////////////////////        ///////
                PenaltyDto penaltyDto = new PenaltyDto(penaltyId,orderId,storeId,penaltyMount,date);

                boolean isPenaltySaved = penaltyBO.savePenalty(penaltyDto);

                if (!isPenaltySaved) {
                    new Alert(Alert.AlertType.ERROR,"Failed to save penalty").show();
                    return;
                }

                PaymentDto paymentDto = new PaymentDto(paymentId,orderId,promotionAmount,penaltyMount,amount,method,status,date);
                boolean isSave = paymentBO.savePayment(paymentDto);
                System.out.println("Saving payment with ID: " + paymentId);

                if (isSave) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Payment Saved").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Payment Not Saved").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Payment Not Saved").show();
                e.printStackTrace();
            }
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = paymentBO.getNextId();
        lblPayId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String paymentId = lblPayId.getText();
           String orderId = cmOrderId.getValue();
           double promotionAmount = Double.parseDouble(txtProId.getText());
           double penaltyAmount = Double.parseDouble(txtPenaltyId.getText());
           double amount = Double.parseDouble(txtAmount.getText());
           String method = comMethod.getValue();
           String status = comStatu.getValue();
           String date = txtDate.getText();

           try {
           //////////////////////////////////////////////////////////////////////////////////////
               String penaltyId =penaltyModel.getPenaltyIdByOrderIdAndDate(orderId, date);
           //////////////////////////////////////////////////////////////////////////////////////
               if (penaltyId == null) {
                   new Alert(Alert.AlertType.ERROR, "Penalty ID not found for this order and date").show();
                   return;
               }

               String storeId = storeModel.getStoreId(orderId);

               PenaltyDto penaltyDto = new PenaltyDto(penaltyId,orderId,storeId,penaltyAmount,date);
               boolean isPenaltyUpdated = penaltyBO.updatePenalty(penaltyDto);
               if (!isPenaltyUpdated) {
                   new Alert(Alert.AlertType.ERROR,"Failed to update penalty").show();
                   return;
               }

               PaymentDto paymentDto = new PaymentDto(paymentId,orderId,promotionAmount,penaltyAmount,amount,method,status,date);

               boolean isUpdate = paymentBO.updatePayment(paymentDto);
               if (isUpdate) {
                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Payment updated").show();
               }else {
                   new Alert(Alert.AlertType.ERROR, "Payment Not updated").show();
               }
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Payment Not updated").show();
               e.printStackTrace();
           }
       }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this payment?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String paymentId = lblPayId.getText();
                boolean isDelete = paymentBO.deletePayment(paymentId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Payment Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Payment Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Payment").show();
                e.printStackTrace();
            }
        }

    }

    public void tblOnclick(MouseEvent mouseEvent) {
        PaymentTM selectedItem = tblPayment.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblPayId.setText(selectedItem.getPaymentId());
            cmOrderId.setValue(selectedItem.getOrderId());
            txtProId.setText(String.valueOf(selectedItem.getPromotionAmount()));
            txtPenaltyId.setText(String.valueOf(selectedItem.getPenaltyAmount()));
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            comMethod.setValue(selectedItem.getMethod());
            txtDate.setText(selectedItem.getDate());
            comStatu.setValue(selectedItem.getStatus());


            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
        }

    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnReport.setDisable(true);

        cmOrderId.setValue(null);
        txtProId.setText("");
        txtPenaltyId.setText("");
        txtAmount.setText("");
        comMethod.setValue(null);
        comStatu.setValue(null);
        txtDate.setText("");
        lblService.setText("");
        lblItem.setText("");
    }

    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> list = orderModel.getAllOrderIds();
        ObservableList<String> orderIds = FXCollections.observableArrayList();
        orderIds.addAll(list);
        cmOrderId.setItems(orderIds);
    }
}
