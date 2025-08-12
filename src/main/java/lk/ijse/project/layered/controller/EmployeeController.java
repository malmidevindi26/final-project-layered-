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
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.dto.EmployeeDto;
import lk.ijse.project.layered.dto.tm.CustomerTM;
import lk.ijse.project.layered.dto.tm.EmployeeTM;
import lk.ijse.project.layered.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    public Label lblId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtSalary;
    public TextField txtDate;
    public TextField txtRole;

    private final EmployeeModel employeeModel = new EmployeeModel();
    private final EmployeeBO employeeBO = BOFactory.getInstance().getBO(BOType.EMPLOYEE);

    public TableView <EmployeeTM>tblEmployee;
    public TableColumn <EmployeeTM, String> colId;
    public TableColumn  <EmployeeTM, String> colName;
    public TableColumn  <EmployeeTM, String> colAddress;
    public TableColumn  <EmployeeTM, String> colContact;
    public TableColumn  <EmployeeTM, Double> colSalary;
    public TableColumn  <EmployeeTM, String> colDate;
    public TableColumn  <EmployeeTM, String> colRole;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public ComboBox <String> comRole;

    private final String namePattern = "^[A-Za-z ]+$";
    //private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String addressPattern = "^[\\w\\s\\-\\.,#/]+$";
    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String salaryPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnMail;
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtName.setStyle("-fx-text-fill: black;");
        txtAddress.setStyle("-fx-text-fill: black;");
        txtContact.setStyle("-fx-text-fill: black;");
        txtSalary.setStyle("-fx-text-fill: black;");
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
        if (!txtSalary.getText().matches(salaryPattern)) {
            txtSalary.setStyle("-fx-text-fill: red;");
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

        comRole.getItems().addAll("Helper", "Washer", "Manager", "Ironer", "Cleaner");

       colId.setText("EmpId");
       colName.setText("Name");
       colAddress.setText("Address");
       colContact.setText("Contact");
       colSalary.setText("Salary");
       colDate.setText("Date");
       colRole.setText("Role");

       colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
       colName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
       colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
       colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
       colDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
       colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

      try {
          resetPage();
      }catch (Exception e){
          new Alert(Alert.AlertType.ERROR, "Failed to load Employee Details").show();
          e.printStackTrace();
      }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblEmployee.setItems(FXCollections.observableArrayList(
                employeeBO.getAllEmployees().stream().map(employeeDto -> new EmployeeTM(
                        employeeDto.getEmployeeId(),
                        employeeDto.getName(),
                        employeeDto.getAddress(),
                        employeeDto.getContact(),
                        employeeDto.getSalary(),
                        employeeDto.getHireDate(),
                        employeeDto.getRole()
                )).toList()
        ));

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String employeeId = lblId.getText();
           String name = txtName.getText();
           String address = txtAddress.getText();
           String contact = txtContact.getText();
           double salary = Double.parseDouble(txtSalary.getText());
           String hireDate = txtDate.getText();
           String role = comRole.getValue();

           EmployeeDto employeeDto = new EmployeeDto(employeeId, name, address, contact, salary, hireDate, role);

           try {
               employeeBO.saveEmployee(employeeDto);
//               boolean isSave = employeeModel.saveEmployee(employeeDto);
//               if (isSave) {
                   resetPage();
                   new Alert(Alert.AlertType.INFORMATION, "Employee has been saved successfully").show();
//               }else {
//                   new Alert(Alert.AlertType.ERROR, "Employee has not been saved").show();
//               }

           }catch (SQLException | ClassNotFoundException e){
               new Alert(Alert.AlertType.ERROR, "Employee has not been saved").show();
               e.printStackTrace();
           } catch (Exception e) {
               System.out.println(e.getMessage());
//                e.printStackTrace();
               new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
           }
       }
    }

    private void loadNextId() throws Exception {
//        String nextId = employeeModel.getNextId();
        String nextId = employeeBO.getNextId();
        lblId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws Exception {
        resetPage();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this customer?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String employeeId = lblId.getText();
//                boolean isDelete = employeeModel.deleteEmployee(employeeId);
                boolean isDelete = employeeBO.deleteEmployee(employeeId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
                e.printStackTrace();
            }
        }
    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String employeeId = lblId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            double salary = Double.parseDouble(txtSalary.getText());
            String hireDate = txtDate.getText();
            String role = comRole.getValue();

            EmployeeDto employeeDto = new EmployeeDto(employeeId, name, address, contact, salary, hireDate, role);

            try {
//                boolean isUpdate = employeeModel.updateEmployee(employeeDto);
                employeeBO.updateEmployee(employeeDto);
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Employee has been updated successfully").show();


            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "Employee has not been updated").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getEmpId());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtContact.setText(selectedItem.getContact());
            txtSalary.setText(String.valueOf(selectedItem.getSalary()));
            txtDate.setText(selectedItem.getHireDate());
            comRole.setValue(selectedItem.getRole());

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSave.setDisable(true);
            btnReport.setDisable(false);
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
        txtAddress.setText("");
        txtContact.setText("");
        txtSalary.setText("");
        txtDate.setText("");
        comRole.setValue("");

    }


}
