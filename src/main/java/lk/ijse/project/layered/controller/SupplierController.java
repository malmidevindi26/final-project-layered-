package lk.ijse.project.layered.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.layered.dto.SupplierDto;
import lk.ijse.project.layered.dto.tm.SupplierTM;
import lk.ijse.project.layered.model.InventoryModel;
import lk.ijse.project.layered.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    public Label lblSupId;
    public TextField txtItemId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtAmount;
    public TextField txtDate;

    private final SupplierModel supplierModel = new SupplierModel();
    private final InventoryModel inventoryModel = new InventoryModel();

    public TableView <SupplierTM> tblSupplier;
    public TableColumn <SupplierTM, String> colSupId;
    public TableColumn <SupplierTM, String> colItemId;
    public TableColumn <SupplierTM, String> colName;
    public TableColumn <SupplierTM, String> colAddress;
    public TableColumn <SupplierTM, String> colContact;
    public TableColumn <SupplierTM, Double> colAmount;
    public TableColumn <SupplierTM, String> colDate;
    public Button btnUpdete;
    public Button btnDelete;
    public Button btnSave;
    public ComboBox<String> cmItemId;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String addressPattern = "^[\\w\\s\\-\\.,#/]+$";
    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtName.setStyle("-fx-text-fill: black;");
        txtAddress.setStyle("-fx-text-fill: black;");
        txtContact.setStyle("-fx-text-fill: black;");
        txtAmount.setStyle("-fx-text-fill: black;");
        txtDate.setStyle("-fx-text-fill: black;");


        String addressPattern = "^[\\w\\s\\-\\.,#/]+$";
        String phonePattern = "^(0|\\+94)?[1-9]\\d{8}$";

        if (!txtName.getText().matches(namePattern)) {
            txtName.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!txtAddress.getText().matches(addressPattern)) {
            txtAddress.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!txtContact.getText().matches(phonePattern)) {
            txtContact.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

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
       colSupId.setText("Supplier Id");
       colItemId.setText("Item Id");
       colName.setText("Name");
       colAddress.setText("Address");
       colContact.setText("Contact");
       colAmount.setText("Amount");
       colDate.setText("Date");

       colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
       colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
       colName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
       colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
       colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
       colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

       try {
         resetPage();
           loadSupplierIds();
       }catch (Exception e){
           new Alert(Alert.AlertType.ERROR,"Failed to load data").show();
           e.printStackTrace();
       }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList <SupplierDto> supplierDtoArrayList = supplierModel.getAllSupplier();
        ObservableList<SupplierTM> list = FXCollections.observableArrayList();

       for (SupplierDto supplierDto : supplierDtoArrayList) {
           SupplierTM supplierTM = new SupplierTM(
                   supplierDto.getSupplierId(),supplierDto.getItemId(),supplierDto.getName(),
                   supplierDto.getAddress(),supplierDto.getContact(),supplierDto.getAmount(),supplierDto.getDate()
           );
           list.add(supplierTM);
       }
       tblSupplier.setItems(list);
    }

    public void btnSaveAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String supplierId = lblSupId.getText();
           String itemId = cmItemId.getValue();
           String name = txtName.getText();
           String address = txtAddress.getText();
           String contact = txtContact.getText();
           double amount = Double.parseDouble(txtAmount.getText());
           String date = txtDate.getText();

           SupplierDto supplierDto = new SupplierDto(supplierId, itemId, name, address, contact, amount, date);

           try {
               boolean isSave = supplierModel.saveSupplier(supplierDto);
               if (isSave) {
                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Supplier has been saved successfully").show();
               }else {
                   new Alert(Alert.AlertType.ERROR, "Supplier has not been saved").show();
               }
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Supplier has not been saved").show();
               e.printStackTrace();
           }
       }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = supplierModel.getNextId();
        lblSupId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String supplierId = lblSupId.getText();
            String itemId = cmItemId.getValue();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            String date = txtDate.getText();

            SupplierDto supplierDto = new SupplierDto(supplierId, itemId, name, address, contact, amount, date);

            try {
                boolean isUpdate = supplierModel.updateSupplier(supplierDto);
                if (isUpdate) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Supplier has been updated successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Supplier has not been updated").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Supplier has not been updated").show();
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this supplier?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String supplierId = lblSupId.getText();
                boolean isDelete = supplierModel.deleteSupplier(supplierId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Supplier Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier").show();
                e.printStackTrace();
            }
        }
    }

    public void btnOnClick(MouseEvent mouseEvent) {
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblSupId.setText(selectedItem.getSupplierId());
            cmItemId.setValue(selectedItem.getItemId());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtContact.setText(selectedItem.getContact());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtDate.setText(selectedItem.getDate());

            btnDelete.setDisable(false);
            btnReport.setDisable(false);
            btnUpdete.setDisable(false);
            btnSave.setDisable(true);
        }
    }
    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdete.setDisable(true);
        btnReport.setDisable(true);

        cmItemId.setValue(null);
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtAmount.setText("");
        txtDate.setText("");
    }
    private void loadSupplierIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> list = inventoryModel.getAllSupplierIds();
        ObservableList<String> supplierIds = FXCollections.observableArrayList();
        supplierIds.addAll(list);
        cmItemId.setItems(supplierIds);
    }
}
