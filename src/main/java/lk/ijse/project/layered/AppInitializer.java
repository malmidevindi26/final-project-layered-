package lk.ijse.project.layered;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {

        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        Parent loadPage = FXMLLoader.load(getClass().getResource("/view/LoadPage.fxml"));
        stage.setScene(new Scene(loadPage));
        stage.show();

        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
                return  new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("Dashboard");
            stage.setScene(value);
        });
        loadingTask.setOnFailed(event -> {
            new Alert(Alert.AlertType.ERROR, "Loading Error").show();
        });

        new Thread(loadingTask).start();
        
    }
}
