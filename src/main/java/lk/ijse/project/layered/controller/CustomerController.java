package lk.ijse.project.layered.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.project.layered.bo.BOFactory;
import lk.ijse.project.layered.bo.BOType;
import lk.ijse.project.layered.bo.custom.CustomerBO;
import lk.ijse.project.layered.bo.exception.DuplicateException;
import lk.ijse.project.layered.bo.exception.InUseException;
import lk.ijse.project.layered.db.DBConnection;
import lk.ijse.project.layered.dto.CustomerDto;
import lk.ijse.project.layered.dto.tm.CustomerTM;
//import lk.ijse.project.layered.model.CustomerModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class CustomerController implements Initializable {

    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public Label lblId;
    public Button btnReset;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnDelete;

    @FXML

   // private final CustomerModel customerModel = new CustomerModel();
    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER);


    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM, String> colId;
    public TableColumn<CustomerTM, String> colName;
    public TableColumn<CustomerTM, String> colAddress;
    public TableColumn<CustomerTM, String> colContact;
    public TextField txtEmail;
    public TableColumn <CustomerTM, String> colEmail;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String addressPattern = "^[\\w\\s\\-\\.,#/]+$";
    public Button btnReport;
    public Button btnMail;

    private boolean validateInput() {
        boolean isValid = true;

        txtName.setStyle("-fx-text-fill: black;");
        txtAddress.setStyle("-fx-text-fill: black;");
        txtContact.setStyle("-fx-text-fill: black;");
        txtEmail.setStyle("-fx-text-fill: black;");


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

        if (!txtEmail.getText().matches(emailPattern)) {
            txtEmail.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setText("CustId");
        colName.setText("Name");
        colAddress.setText("Address");
        colContact.setText("Contact");
        colEmail.setText("Email");

        colId.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data").show();


        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
//        ArrayList<CustomerDto> customerDtoArrayList = customerModel.getAllCustomers();
//        ObservableList<CustomerTM> list = FXCollections.observableArrayList();
//
//        for (CustomerDto customerDto : customerDtoArrayList) {
//            CustomerTM customerTM = new CustomerTM(
//                    customerDto.getCustomerId(), customerDto.getName(),
//                    customerDto.getAddress(), customerDto.getContact(),customerDto.getEmail()
//            );
//            list.add(customerTM);
//        }
//        tblCustomer.setItems(list);
        tblCustomer.setItems(FXCollections.observableArrayList(
                customerBO.getAllCustomers().stream().map(customerDto -> new CustomerTM(
                        customerDto.getCustomerId(),
                        customerDto.getName(),
                        customerDto.getAddress(),
                        customerDto.getContact(),
                        customerDto.getEmail()
                )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (validateInput()) {
            String customerId = lblId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();

            CustomerDto customerDto = new CustomerDto(customerId, name, address, contact, email);

            try {
                //boolean isSave = customerModel.saveCustomer(customerDto);
                customerBO.saveCustomer(customerDto);
//                if (isSave) {
//                    resetPage();
//                    new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
//
//                } else {
//                    new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
//                }
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            } catch (DuplicateException e) {
                System.out.println(e.getMessage());
//                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
            }

        }
    }

    private void loadNextId() throws Exception {
       // String nextId = customerModel.getNextId();
        String nextId = customerBO.getNextId();
        lblId.setText(nextId);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this customer?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String customerId = lblId.getText();
//                boolean isDelete = customerModel.deleteCustomer(customerId);
                boolean isDelete = customerBO.deleteCustomer(customerId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Deleted").show();
                }
            }  catch (InUseException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
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
        btnMail.setDisable(true);
        
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        
        
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       if (validateInput()) {
           String customerId = lblId.getText();
           String name = txtName.getText();
           String address = txtAddress.getText();
           String contact = txtContact.getText();
           String email = txtEmail.getText();

           CustomerDto customerDto = new CustomerDto(customerId, name, address, contact, email);

           try {
              customerBO.updateCustomer(customerDto);

              resetPage();
               new Alert(
                       Alert.AlertType.INFORMATION, "Customer update successfully..!"
               ).show();
           } catch (Exception e) {
               e.printStackTrace();
               new Alert(Alert.AlertType.ERROR, "Fail to update customer").show();
           }

       }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws Exception {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getCid());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtContact.setText(selectedItem.getContact());
            txtEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReport.setDisable(false);
            btnMail.setDisable(false);
        }
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if(customerBO == null) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SendMail.fxml"));
            Parent load = fxmlLoader.load();

            SendMailController controller = fxmlLoader.getController();
            controller.setCustomerEmail(selectedItem.getEmail());

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Mail");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window window = txtContact.getScene().getWindow();
            stage.initOwner(window);

            stage.showAndWait();
            resetPage();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        CustomerTM selectedId = tblCustomer.getSelectionModel().getSelectedItem();
        if(customerBO == null) {
            return;
        }
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/Report/ProjectCustomer.jrxml")
            );
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_customer_id", selectedId.getCid());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

