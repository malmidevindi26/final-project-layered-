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
import lk.ijse.project.layered.bo.custom.PenaltyBO;
import lk.ijse.project.layered.bo.custom.StoreManagementBO;
import lk.ijse.project.layered.dto.PenaltyDto;
import lk.ijse.project.layered.dto.tm.CustomerTM;
import lk.ijse.project.layered.dto.tm.PenaltyTm;
//import lk.ijse.project.layered.model.OrderModel;
//import lk.ijse.project.layered.model.PenaltyModel;
//import lk.ijse.project.layered.model.StoreManagementModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PenaltyController implements Initializable {
    public Label lblPenaltyId;
    public TextField txtOrderId;
    public TextField txtStoreId;
    public TextField txtAmount;
    public TextField txtReason;
   // private final PenaltyModel penaltyModel=new PenaltyModel();
   // private final OrderModel orderModel=new OrderModel();
   // private final StoreManagementModel storeManagementModel=new StoreManagementModel();

    private final PenaltyBO penaltyBO = BOFactory.getInstance().getBO(BOType.PENALTY);
    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);
    private final StoreManagementBO storeManagementBO = BOFactory.getInstance().getBO(BOType.STOREMANAGEMENT);

    public TableView <PenaltyTm> tblPenalty;
    public TableColumn <PenaltyTm, String> colPenaltyId;
    public TableColumn <PenaltyTm, String> colOrderId;
    public TableColumn <PenaltyTm, String> colStoreId;
    public TableColumn <PenaltyTm, Double> colAmount;
    public TableColumn <PenaltyTm, String> colDate;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public ComboBox <String> cmOrderId;
    public ComboBox <String> cmStoreId;

    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtAmount.setStyle("-fx-text-fill: black;");
        txtReason.setStyle("-fx-text-fill: black;");

        if (!txtAmount.getText().matches(amountPattern)) {
            txtAmount.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtReason.getText().matches(datePattern)) {
            txtReason.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPenaltyId.setText("Penalty Id");
        colOrderId.setText("Order Id");
        colStoreId.setText("Store Id");
        colAmount.setText("Amount");
        colDate.setText("Date");

        colPenaltyId.setCellValueFactory(new PropertyValueFactory<>("penaltyId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colStoreId.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("reason"));

        try {
            resetPage();
            loadOrderIds();
            loadStoreIds();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Failed to load data").show();
            e.printStackTrace();
        }

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
//        ArrayList <PenaltyDto> penaltyDtoArrayList = penaltyModel.getAllPenalty();
//        ObservableList<PenaltyTm> list = FXCollections.observableArrayList();
//
//        for (PenaltyDto penaltyDto : penaltyDtoArrayList) {
//            PenaltyTm penaltyTm = new PenaltyTm(
//                    penaltyDto.getPenaltyId(),penaltyDto.getOrderId(),penaltyDto.getStoreId(),
//                    penaltyDto.getAmount(),penaltyDto.getReason()
//            );
//            list.add(penaltyTm);
//        }
//        tblPenalty.setItems(list);
        tblPenalty.setItems(FXCollections.observableArrayList(
                penaltyBO.getAllPenalties().stream().map(penaltyDto -> new PenaltyTm(
                       penaltyDto.getPenaltyId(),
                       penaltyDto.getOrderId(),
                       penaltyDto.getStoreId(),
                       penaltyDto.getAmount(),
                       penaltyDto.getReason()
                )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String penaltyId = lblPenaltyId.getText();
            String orderId = cmOrderId.getValue();
            String storeId = cmStoreId.getValue();
            double amount = Double.parseDouble(txtAmount.getText());
            String reason = txtReason.getText();

            PenaltyDto penaltyDto = new PenaltyDto(penaltyId, orderId, storeId, amount, reason);
            try {
                boolean isSave =  penaltyBO.savePenalty(penaltyDto);
                if(isSave){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Penalty Saved").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Penalty not Saved").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,"Penalty not Saved").show();
                e.printStackTrace();
            }
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = penaltyBO.getNextId();
        lblPenaltyId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String penaltyId = lblPenaltyId.getText();
           String orderId = cmOrderId.getValue();
           String storeId = cmStoreId.getValue();
           double amount = Double.parseDouble(txtAmount.getText());
           String reason = txtReason.getText();

           PenaltyDto penaltyDto = new PenaltyDto(penaltyId, orderId, storeId, amount, reason);
           try {
               boolean isUpdate =  penaltyBO.updatePenalty(penaltyDto);
               if(isUpdate){
                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION,"Penalty updated").show();
               }else {
                   new Alert(Alert.AlertType.ERROR,"Penalty not updated").show();
               }
           }catch (Exception e){
               new Alert(Alert.AlertType.ERROR,"Penalty not updated").show();
               e.printStackTrace();
           }
       }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this penalty?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String penaltyId = lblPenaltyId.getText();
                boolean isDelete = penaltyBO.deletePenalty(penaltyId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "penalty Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "penalty Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete penalty").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        PenaltyTm selectedItem = tblPenalty.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblPenaltyId.setText(selectedItem.getPenaltyId());
            cmOrderId.setValue(selectedItem.getOrderId());
            cmStoreId.setValue(selectedItem.getStoreId());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtReason.setText(selectedItem.getReason());

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
        cmStoreId.setValue(null);
        txtAmount.setText("");
        txtReason.setText("");
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        List<String> list = orderBO.getAllOrderIds();
        ObservableList<String> orderIds = FXCollections.observableArrayList();
        orderIds.addAll(list);
        cmOrderId.setItems(orderIds);
    }
    private void loadStoreIds() throws SQLException, ClassNotFoundException {
        List<String> list = storeManagementBO.getAllStoreIds();
        ObservableList<String> storeIds = FXCollections.observableArrayList();
        storeIds.addAll(list);
        cmStoreId.setItems(storeIds);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
