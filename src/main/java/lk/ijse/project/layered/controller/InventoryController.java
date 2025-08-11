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
import lk.ijse.project.layered.bo.custom.InventoryBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.dto.InventoryDto;
import lk.ijse.project.layered.dto.tm.InventoryTM;
import lk.ijse.project.layered.model.InventoryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    public Label lblId;
   // public TextField txtItemId;
    public TextField txtName;
    public TextField txtManuDate;
    public TextField txtExDate;
    public TextField txtStatus;
    public TextField txtQty;

    private final InventoryModel inventoryModel = new InventoryModel();
    private final InventoryBO inventoryBO = BOFactory.getInstance().getBO(BOType.INVENTORY);

    public TableView <InventoryTM>tblInventory;
    public TableColumn <InventoryTM, String> colInventoryId;
    //public TableColumn <InventoryTM, String> colItemId;
    public TableColumn <InventoryTM, String> colName;
    public TableColumn <InventoryTM, String> colManuDate;
    public TableColumn <InventoryTM, String> colExDate;
    public TableColumn <InventoryTM, String> colStatus;
    public TableColumn <InventoryTM, Double> colQty;
    public TextField txtItemId;
    public TableColumn <InventoryTM, Double>colPrice;
    public TextField txtPrice;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;
    public ComboBox <String>comStatus;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String qtyPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtName.setStyle("-fx-text-fill: black;");
        txtManuDate.setStyle("-fx-text-fill: black;");
        txtExDate.setStyle("-fx-text-fill: black;");
        txtQty.setStyle("-fx-text-fill: black;");
        txtPrice.setStyle("-fx-text-fill: black;");


        String addressPattern = "^[\\w\\s\\-\\.,#/]+$";
        String phonePattern = "^(0|\\+94)?[1-9]\\d{8}$";

        if (!txtName.getText().matches(namePattern)) {
            txtName.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!txtManuDate.getText().matches(datePattern)) {
            txtManuDate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!txtExDate.getText().matches(datePattern)) {
            txtExDate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtQty.getText().matches(qtyPattern)) {
            txtQty.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtPrice.getText().matches(qtyPattern)) {
            txtPrice.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comStatus.getItems().addAll("Out of Stock","In Stock","Not Available");
        colInventoryId.setText("Inventory ID");
       // colItemId.setText("Item ID");
        colName.setText("Name");
        colManuDate.setText("Manu Date");
        colExDate.setText("Ex Date");
        colStatus.setText("Status");
        colQty.setText("Quantity");
        colPrice.setText("Price");

        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
       // colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colManuDate.setCellValueFactory(new PropertyValueFactory<>("manufacturedDate"));
        colExDate.setCellValueFactory(new PropertyValueFactory<>("expaireDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
          resetPage();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Failed to load Inventory details").show();
            e.printStackTrace();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDto> inventoryDtoArrayList = inventoryModel.getAllInventories();
        ObservableList<InventoryTM> list = FXCollections.observableArrayList();

        for(InventoryDto inventoryDto : inventoryDtoArrayList) {
            InventoryTM inventoryTM = new InventoryTM(
                    inventoryDto.getItemId(),inventoryDto.getName(),
                    inventoryDto.getManufacturerdDate(),inventoryDto.getExpaireDate(),
                    inventoryDto.getStatus(),inventoryDto.getQuantity(),inventoryDto.getPrice()
            );
            list.add(inventoryTM);
        }
        tblInventory.setItems(list);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String inventoryId = lblId.getText();
           String name = txtName.getText();
           String manufactureDate = txtManuDate.getText();
           String expaireDate = txtExDate.getText();
           String status = comStatus.getValue();
           double quantity = Double.parseDouble(txtQty.getText());
           double price = Double.parseDouble(txtPrice.getText());

           InventoryDto inventoryDto = new InventoryDto(inventoryId,name,manufactureDate,expaireDate,status,quantity,price);

           try {
                 inventoryBO.saveInventory(inventoryDto);
//               boolean isSave = inventoryModel.saveInventory(inventoryDto);
//               if (isSave) {
                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Inventory Saved").show();

//               }else {
//                   new Alert(Alert.AlertType.ERROR, "Inventory Not Saved").show();
//               }
           }catch (Exception e){
               new Alert(Alert.AlertType.ERROR, "Inventory Not Saved").show();
               e.printStackTrace();
           }
       }

    }

    private void loadNextId() throws Exception {
       // String nextId = inventoryModel.getNextId();
        String nextId = inventoryBO.getNextId();
        lblId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws Exception {
        resetPage();
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        InventoryTM selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getInventoryId());
            txtName.setText(selectedItem.getName());
            txtManuDate.setText(selectedItem.getManufacturedDate());
            txtExDate.setText(selectedItem.getExpaireDate());
            comStatus.setValue(selectedItem.getStatus());
            txtQty.setText(String.valueOf(selectedItem.getQuantity()));
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String inventoryId = lblId.getText();
           String name = txtName.getText();
           String manufactureDate = txtManuDate.getText();
           String expaireDate = txtExDate.getText();
           String status = comStatus.getValue();
           double quantity = Double.parseDouble(txtQty.getText());
           double price = Double.parseDouble(txtPrice.getText());

           InventoryDto inventoryDto = new InventoryDto(inventoryId,name,manufactureDate,expaireDate,status,quantity,price);

           try {
              inventoryBO.updateInventory(inventoryDto);

                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Inventory updated").show();

           }catch (Exception e){
               new Alert(Alert.AlertType.ERROR, "Inventory Not updated").show();
               e.printStackTrace();
           }
       }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this inventory?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String inventoryId = lblId.getText();
//                boolean isDelete = inventoryModel.deleteInventory(inventoryId);
                boolean isDelete = inventoryBO.deleteInventory(inventoryId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Inventory Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Inventory Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Inventory").show();
                e.printStackTrace();
            }
        }
    }

    private void resetPage() throws Exception {
        loadNextId();
        loadTableData();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        btnReport.setDisable(true);

        txtName.setText("");
        txtManuDate.setText("");
        txtExDate.setText("");
        comStatus.setValue("");
        txtQty.setText("");
        txtPrice.setText("");


    }
}
