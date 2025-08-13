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
import lk.ijse.project.layered.bo.custom.UtilityExpenseBO;
import lk.ijse.project.layered.dto.UtilityExpenseDto;
import lk.ijse.project.layered.dto.tm.CustomerTM;
import lk.ijse.project.layered.dto.tm.UtilityTM;
//import lk.ijse.project.layered.model.UtilityExpenseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UtilityExpenseController implements Initializable {
    public Label lblId;
    public TextField txtType;
    public TextField txtAmount;
    public TextField txtBillingDate;

  //  private final UtilityExpenseModel utilityExpenseModel = new UtilityExpenseModel();
    private final UtilityExpenseBO utilityExpenseBO = BOFactory.getInstance().getBO(BOType.UTILITYEXPENSE);

    public TableView<UtilityTM> tblUtility;
    public TableColumn <UtilityTM,String> colId;
    public TableColumn <UtilityTM,String> colType;
    public TableColumn <UtilityTM,Double> colAmount;
    public TableColumn <UtilityTM,String> colBilling;
    public TableColumn <UtilityTM,String> colDue;
    public TableColumn <UtilityTM,String> colStatus;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;

    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    private final String namePattern = "^[A-Za-z ]+$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtAmount.setStyle("-fx-text-fill: black;");
        txtType.setStyle("-fx-text-fill: black;");
        txtBillingDate.setStyle("-fx-text-fill: black;");

        if (!txtAmount.getText().matches(amountPattern)) {
            txtAmount.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtBillingDate.getText().matches(datePattern)) {
            txtBillingDate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtType.getText().matches(namePattern)) {
            txtType.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setText("Utility ID");
        colType.setText("Type");
        colAmount.setText("Amount");
        colBilling.setText("Billing");


        colId.setCellValueFactory(new PropertyValueFactory<>("utilityId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBilling.setCellValueFactory(new PropertyValueFactory<>("billingDate"));

        try {
           resetPage();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to load Employee Details").show();
            e.printStackTrace();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
//        ArrayList<UtilityExpenseDto> utilityExpenseDtoArrayList = utilityExpenseModel.getAllUtility();
//        ObservableList<UtilityTM> list = FXCollections.observableArrayList();
//
//        for (UtilityExpenseDto utilityExpenseDto : utilityExpenseDtoArrayList) {
//            UtilityTM utilityTM = new UtilityTM(
//                   utilityExpenseDto.getExpenseId(),utilityExpenseDto.getExpenseType(),utilityExpenseDto.getAmount(),
//                    utilityExpenseDto.getBillingDate()
//            );
//            list.add(utilityTM);
//        }
//        tblUtility.setItems(list);
        tblUtility.setItems(FXCollections.observableArrayList(
                utilityExpenseBO.getAllExpenses().stream().map(utilityExpenseDto -> new UtilityTM(
                        utilityExpenseDto.getExpenseId(),
                        utilityExpenseDto.getExpenseType(),
                        utilityExpenseDto.getAmount(),
                        utilityExpenseDto.getBillingDate()
                )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String utilityId = lblId.getText();
            String type = txtType.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            String billDate = txtBillingDate.getText();


            UtilityExpenseDto utilityExpenseDto = new UtilityExpenseDto(utilityId, type, amount, billDate);

            try{
                boolean isSave = utilityExpenseBO.saveExpense(utilityExpenseDto);
                if(isSave){
                    resetPage();

                    new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Not Save").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "Not Save").show();
                e.printStackTrace();
            }
        }

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = utilityExpenseBO.getNextId();
        lblId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String utilityId = lblId.getText();
           String type = txtType.getText();
           double amount = Double.parseDouble(txtAmount.getText());
           String billDate = txtBillingDate.getText();

           UtilityExpenseDto utilityExpenseDto = new UtilityExpenseDto(utilityId, type, amount, billDate);

           try{
               boolean isUpdate = utilityExpenseBO.updateExpense(utilityExpenseDto);
               if(isUpdate){
                   resetPage();

                   new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
               }else {
                   new Alert(Alert.AlertType.ERROR, "Not Update").show();
               }
           }catch (Exception e){
               new Alert(Alert.AlertType.ERROR, "Not update").show();
               e.printStackTrace();
           }
       }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this utility expense ?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String utilityId = lblId.getText();
                boolean isDelete = utilityExpenseBO.deleteExpense(utilityId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Utility  Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Utility Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Utility").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        UtilityTM selectedItem = tblUtility.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getUtilityId());
            txtType.setText(selectedItem.getType());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtBillingDate.setText(selectedItem.getBillingDate());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
        }
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        txtType.setText("");
        txtAmount.setText("");
        txtBillingDate.setText("");

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnReport.setDisable(true);
        btnSave.setDisable(false);


    }
}
