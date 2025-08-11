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
import lk.ijse.project.layered.bo.custom.MachineBO;
import lk.ijse.project.layered.dto.MachineDto;
import lk.ijse.project.layered.dto.tm.MachineTM;
import lk.ijse.project.layered.model.MachineModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MachineController implements Initializable {
    public Label lblId;
    public TextField txtType;
    public TextField txtStatus;
    public TextField txtCost;
    public TextField txtDate;


    public TableView <MachineTM> tblMachine;
    public TableColumn <MachineTM, String> colId;
    public TableColumn <MachineTM, String> colType;
    public TableColumn <MachineTM, String> colStatus;
    public TableColumn <MachineTM, Double> colCost;
    public TableColumn <MachineTM, String> colDate;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;
    public ComboBox <String>comStatus;
    public ComboBox <String>comType;
    public Button btnReport;

    MachineModel machineModel = new MachineModel();
    private final MachineBO machineBO = BOFactory.getInstance().getBO(BOType.MACHINE);

    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String costPattern = "^[0-9]+(\\.[0-9]{1,2})?$";


    private boolean validateInput() {
        boolean isValid = true;

        txtCost.setStyle("-fx-text-fill: black;");
        txtDate.setStyle("-fx-text-fill: black;");

        String addressPattern = "^[\\w\\s\\-\\.,#/]+$";
        String phonePattern = "^(0|\\+94)?[1-9]\\d{8}$";

        if (!txtDate.getText().matches(datePattern)) {
            txtDate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!txtCost.getText().matches(costPattern)) {
            txtCost.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comStatus.getItems().addAll("Working", "Under Repair", "Not Available");
        comType.getItems().addAll("Washing Machine", "Drying Machines", "Ironing Machines");

        colId.setText("Machine ID");
        colType.setText("Type");
        colStatus.setText("Status");
        colCost.setText("Cost");
        colDate.setText("Date");

        colId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
           resetPage();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MachineDto> machineDtoArrayList = machineModel.getAllMachine();
        ObservableList<MachineTM> list = FXCollections.observableArrayList();

        for (MachineDto machineDto : machineDtoArrayList) {
            MachineTM machineTM = new MachineTM(
                    machineDto.getMachineId(),machineDto.getType(),
                    machineDto.getStatus(),machineDto.getCost(),
                    machineDto.getDate()
            );
            list.add(machineTM);
        }
        tblMachine.setItems(list);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String machineId = lblId.getText();
           String machineType = comType.getValue();
           String machineStatus = comStatus.getValue();
           String machineDate = txtDate.getText();

           try {
               double machineCost = Double.parseDouble(txtCost.getText());
               MachineDto machineDto = new MachineDto(machineId, machineType, machineStatus, machineCost, machineDate);
               machineCost = Double.parseDouble(txtCost.getText());

//               boolean isSave = machineModel.saveMachine(machineDto);
               machineBO.saveMachine(machineDto);

                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Machine saved successfully").show();

           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Machine could not be saved").show();
               e.printStackTrace();
           }
       }
    }

    private void loadNextId() throws Exception {
       // String nextId = machineModel.getNextId();
        String nextId = machineBO.getNextId();
        lblId.setText(nextId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws Exception {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String machineId = lblId.getText();
            String machineType = comType.getValue();
            String machineStatus = comStatus.getValue();
            String machineDate = txtDate.getText();

            try {
                double machineCost = Double.parseDouble(txtCost.getText());
                MachineDto machineDto = new MachineDto(machineId, machineType, machineStatus, machineCost, machineDate);
                machineCost = Double.parseDouble(txtCost.getText());
               // boolean isUpdate = machineModel.updateMachine(machineDto);
                machineBO.updateMachine(machineDto);

                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Machine updated successfully").show();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Machine could not be updated").show();
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this machine?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String machineId = lblId.getText();
               // boolean isDelete = machineModel.deleteCustomer(machineId);
                boolean isDelete =  machineBO.deleteMachine(machineId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "machine Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "machine Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete machine").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        MachineTM selectedItem = tblMachine.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getMachineId());
            comType.setValue(selectedItem.getType());
            comStatus.setValue(selectedItem.getStatus());
            txtCost.setText(String.valueOf(selectedItem.getCost()));
            txtDate.setText(selectedItem.getDate());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
        }

    }

    private void resetPage() throws Exception {
        loadNextId();
        loadTableData();

        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnReport.setDisable(true);

        comType.setValue("");
        comStatus.setValue("");
        txtCost.setText("");
        txtDate.setText("");
    }
}
