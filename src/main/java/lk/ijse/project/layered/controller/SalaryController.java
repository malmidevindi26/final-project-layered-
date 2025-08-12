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
import lk.ijse.project.layered.bo.custom.EmployeeBO;
import lk.ijse.project.layered.bo.custom.SalaryBO;
import lk.ijse.project.layered.dto.SalaryDto;
import lk.ijse.project.layered.dto.tm.CustomerTM;
import lk.ijse.project.layered.dto.tm.SalaryTM;
import lk.ijse.project.layered.model.EmployeeModel;
import lk.ijse.project.layered.model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {
    public Label lblSalaryId;
    public TextField txtEmpId;
    public TextField txtAmount;
    public TextField txtDate;
    private final SalaryModel salaryModel = new SalaryModel();
    private final EmployeeModel employeeModel = new EmployeeModel();
    public TableView <SalaryTM> tblSalary;
    public TableColumn <SalaryTM, String> colSalaryId;
    public TableColumn <SalaryTM, String> colEmpId;
    public TableColumn <SalaryTM, Double> colAmount;
    public TableColumn <SalaryTM, String> colDate;
    public ComboBox<String> cmEmployeeId;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;

    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private final SalaryBO salaryBO = BOFactory.getInstance().getBO(BOType.SALARY);
    private final EmployeeBO employeeBO = BOFactory.getInstance().getBO(BOType.EMPLOYEE);

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
        colSalaryId.setText("Salary Id");
        colEmpId.setText("Emp Id");
        colAmount.setText("Amount");
        colDate.setText("Date");

        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            loadNextId();
            loadTableData();
            loadEmployeeIds();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to load data", ButtonType.OK).show();
            e.printStackTrace();
        }

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
//        ArrayList <SalaryDto> salaryDtoArrayList = salaryModel.getAllSalary();
//        ObservableList<SalaryTM> list = FXCollections.observableArrayList();
//
//        for (SalaryDto salaryDto : salaryDtoArrayList) {
//            SalaryTM salaryTM = new SalaryTM(
//                    salaryDto.getSalaryId(),salaryDto.getEmployeeId(),
//                    salaryDto.getAmount(),salaryDto.getDate()
//            );
//            list.add(salaryTM);
//        }
//        tblSalary.setItems(list);

        tblSalary.setItems(FXCollections.observableArrayList(
                salaryBO.getAllSalaries().stream().map(salaryDto -> new SalaryTM(
                        salaryDto.getSalaryId(),
                        salaryDto.getEmployeeId(),
                        salaryDto.getAmount(),
                        salaryDto.getDate()
                )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String salaryId = lblSalaryId.getText();
           String empId =  cmEmployeeId.getValue();
           double amount = Double.parseDouble(txtAmount.getText());
           String date = txtDate.getText();

           SalaryDto salaryDto = new SalaryDto(salaryId, empId, amount, date);

           try {
               boolean isSave = salaryBO.saveSalary(salaryDto);
               if (isSave) {
                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Salary added successfully").show();
               }else {
                   new Alert(Alert.AlertType.ERROR, "Salary could not be added").show();
               }
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Salary could not be added").show();
               e.printStackTrace();
           }
       }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = salaryBO.getNextId();
        lblSalaryId.setText(nextId);
    }


    public void cmEmpIdOnAction(ActionEvent actionEvent) {


    }
    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> list = employeeBO.getAllEmployeeIds();
        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        employeeIds.addAll(list);
        cmEmployeeId.setItems(employeeIds);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String salaryId = lblSalaryId.getText();
            String empId = cmEmployeeId.getValue();
            double amount = Double.parseDouble(txtAmount.getText());
            String date = txtDate.getText();

            SalaryDto salaryDto = new SalaryDto(salaryId, empId, amount, date);

            try {
                boolean isUpdate = salaryBO.updateSalary(salaryDto);
                if (isUpdate) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Salary updated successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Salary could not be updated").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Salary could not be updated").show();
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this salary?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String salaryId = lblSalaryId.getText();
                boolean isDelete = salaryBO.deleteSalary(salaryId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "salary Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "salary Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete salary").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        SalaryTM selectedItem = tblSalary.getSelectionModel().getSelectedItem();
          if (selectedItem != null) {
              lblSalaryId.setText(selectedItem.getSalaryId());
              cmEmployeeId.setValue(selectedItem.getEmpId());
              txtAmount.setText(String.valueOf(selectedItem.getAmount()));
              txtDate.setText(selectedItem.getDate());

              btnSave.setDisable(true);
              btnDelete.setDisable(false);
              btnUpdate.setDisable(false);
              btnReport.setDisable(false);
          }
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();
        cmEmployeeId.setValue(null);
        txtAmount.setText("");
        txtDate.setText("");

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnReport.setDisable(true);
        btnSave.setDisable(false);


    }
}
