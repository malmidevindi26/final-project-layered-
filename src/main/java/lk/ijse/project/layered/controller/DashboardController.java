package lk.ijse.project.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private AnchorPane acnMain;
    public void btnGoCustomer(ActionEvent actionEvent) {
        navigateTo("/view/CustomerPage.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnGoEmployee(ActionEvent actionEvent) {

        navigateTo("/view/EmployeePage.fxml");
    }

    public void btnGoInventory(ActionEvent actionEvent) {

        navigateTo("/view/InventoryPage.fxml");
    }

    public void btnGoItem(ActionEvent actionEvent) {

        navigateTo("/view/ItemPage.fxml");
    }

    public void btnGoMachine(ActionEvent actionEvent) {

        navigateTo("/view/MachinePage.fxml");
    }

    public void btnGoOrder(ActionEvent actionEvent) {

        navigateTo("/view/OrderPage.fxml");
    }

    public void btnGoPayment(ActionEvent actionEvent) {

        navigateTo("/view/PaymentPage.fxml");
    }

    public void btnGoPenalty(ActionEvent actionEvent) {

        navigateTo("/view/PenaltyPage.fxml");
    }

    public void btnGoPromotion(ActionEvent actionEvent) {

        navigateTo("/view/PromotionPage.fxml");
    }

    public void btnGoSalary(ActionEvent actionEvent) {

        navigateTo("/view/SalaryPage.fxml");
    }

    public void btnGoService(ActionEvent actionEvent) {

        navigateTo("/view/ServicePage.fxml");
    }

    public void btnGoStore(ActionEvent actionEvent) {

        navigateTo("/view/StoreManagementPage.fxml");
    }

    public void btnGoSupplier(ActionEvent actionEvent) {

        navigateTo("/view/SupplierPage.fxml");
    }

    public void btnGoUtilityExpense(ActionEvent actionEvent) {
        navigateTo("/view/UtilityExpensePage.fxml");
    }

    public void btnGoLogout(ActionEvent actionEvent) {

        try {
            AnchorPane loginPane = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));

            javafx.stage.Stage stage = (javafx.stage.Stage) acnMain.getScene().getWindow();

            stage.setScene(new javafx.scene.Scene(loginPane));
            stage.centerOnScreen();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Login page not found!").show();
            e.printStackTrace();
        }
    }

    private void navigateTo(String path) {
        try {
            acnMain.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefHeightProperty().bind(acnMain.heightProperty());
            anchorPane.prefWidthProperty().bind(acnMain.widthProperty());

            acnMain.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Page Not Found").show();
            e.printStackTrace();
        }
    }


}
