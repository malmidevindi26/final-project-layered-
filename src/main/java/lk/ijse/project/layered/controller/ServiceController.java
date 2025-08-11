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
import lk.ijse.project.layered.bo.custom.ServiceBO;
import lk.ijse.project.layered.dto.ServiceDto;
import lk.ijse.project.layered.dto.tm.ServiceTM;
import lk.ijse.project.layered.model.ServiceModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {
    public Label lblId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtDesc;

    private final ServiceModel serviceModel = new ServiceModel();
    public TableView <ServiceTM> tblService;
    public TableColumn <ServiceTM,String> colId;
    public TableColumn <ServiceTM,String> colName;
    public TableColumn <ServiceTM,Double> colPrice;
    public TableColumn <ServiceTM,String> colDesc;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;
    public ComboBox <String>comName;

    private final ServiceBO serviceBO = BOFactory.getInstance().getBO(BOType.SERVICE);

    private final String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    private final String namePattern = "^[A-Za-z ]+$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtDesc.setStyle("-fx-text-fill: black;");
        txtPrice.setStyle("-fx-text-fill: black;");

        if (!txtPrice.getText().matches(amountPattern)) {
            txtPrice.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtDesc.getText().matches(namePattern)) {
            txtDesc.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comName.getItems().addAll(" Washing", "Dry Cleaning", " Ironing & Pressing");

        colId.setText("Service Id ");
        colName.setText("Name");
        colPrice.setText("Price");
        colDesc.setText("Description");

        colId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            resetPage();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to load Employee Details").show();
            e.printStackTrace();
        }

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ServiceDto> serviceDtoArrayList = serviceModel.getAllService();
        ObservableList<ServiceTM> list = FXCollections.observableArrayList();

        for (ServiceDto serviceDto : serviceDtoArrayList) {
            ServiceTM serviceTM = new ServiceTM(
                    serviceDto.getServiceId(),serviceDto.getName(),
                    serviceDto.getPrice(),serviceDto.getDescription()
            );
            list.add(serviceTM);
        }
        tblService.setItems(list);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String serviceId = lblId.getText();
            String name = comName.getValue();
            double price = Double.parseDouble(txtPrice.getText());
            String desc = txtDesc.getText();

            ServiceDto serviceDto = new ServiceDto(serviceId, name, price, desc);

            try {
                boolean isSave = serviceBO.saveService(serviceDto);
                if (isSave) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Service saved successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                e.printStackTrace();
            }
        }

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String serviceId = serviceBO.getNextId();
        lblId.setText(serviceId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String serviceId = lblId.getText();
            String name = comName.getValue();
            double price = Double.parseDouble(txtPrice.getText());
            String desc = txtDesc.getText();

            ServiceDto serviceDto = new ServiceDto(serviceId, name, price, desc);

            try {
                boolean isUpdate = serviceBO.updateService(serviceDto);
                if (isUpdate) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Service updated successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this service?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String serviceId = lblId.getText();
                boolean isDelete = serviceBO.deleteService(serviceId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Service Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Service Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Service").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        ServiceTM selectedItem = tblService.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getServiceId());
            comName.setValue(selectedItem.getName());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            txtDesc.setText(selectedItem.getDescription());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
        }

    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        comName.setValue("");
        txtPrice.setText("");
        txtDesc.setText("");

        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnReport.setDisable(true);

    }
}
