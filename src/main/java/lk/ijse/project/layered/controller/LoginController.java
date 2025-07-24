package lk.ijse.project.layered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane acnPage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String username = "1";
        String password = "1";

        String inputUsername = txtUsername.getText();
        String inputPassword = txtPassword.getText();

        if(inputUsername.equals(username) && inputPassword.equals(password)){
            acnPage.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardPage.fxml"));
            acnPage.getChildren().setAll(parent);

        }else {
            System.out.println("Wrong username or password");
        }
    }

}
