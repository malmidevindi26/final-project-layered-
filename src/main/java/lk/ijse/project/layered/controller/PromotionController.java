package lk.ijse.project.layered.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.layered.dto.PromotionDto;
import lk.ijse.project.layered.dto.tm.PromotionTM;
import lk.ijse.project.layered.model.PromotionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PromotionController implements Initializable {
    public Label lblId;
    public TextField txtCode;
    public TextField txtDis;
    public TextField txtDate;
    public TextField txtDesc;

    private final PromotionModel promotionModel = new PromotionModel();
    public TableView <PromotionTM> tblPromotion;
    public TableColumn <PromotionTM,String> colId;
    public TableColumn <PromotionTM,String> colCode;
    public TableColumn <PromotionTM,Integer> colDis;
    public TableColumn <PromotionTM,String> colDate;
    public TableColumn <PromotionTM,String> colDesc;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String discountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btbReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtDis.setStyle("-fx-text-fill: black;");
        txtDate.setStyle("-fx-text-fill: black;");
        txtCode.setStyle("-fx-text-fill: black;");
        txtDesc.setStyle("-fx-text-fill: black;");


        if (!txtCode.getText().matches(namePattern)) {
            txtCode.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtDate.getText().matches(datePattern)) {
            txtDate.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (!txtDis.getText().matches(discountPattern)) {
            txtDis.setStyle("-fx-text-fill: red;");
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
        colId.setText("Promotion Id");
        colCode.setText("Code");
        colDis.setText("Discount");
        colDate.setText("Expire Date");
        colDesc.setText("Description");

        colId.setCellValueFactory(new PropertyValueFactory<>("promotionId"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDis.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            loadNextId();
            loadTableData();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            e.printStackTrace();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<PromotionDto> promotionDtoArrayList = promotionModel.getAllPromotion();
        ObservableList<PromotionTM> list = FXCollections.observableArrayList();

        for (PromotionDto promotionDto : promotionDtoArrayList) {
            PromotionTM promotionTM = new PromotionTM(
                    promotionDto.getPromotionId(),promotionDto.getCode(),
                    promotionDto.getDiscount(),promotionDto.getDate(),
                    promotionDto.getDescription()
            );
            list.add(promotionTM);
        }
        tblPromotion.setItems(list);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String promotionId = lblId.getText();
            String code = txtCode.getText();
            int dis = Integer.parseInt(txtDis.getText());
            String date = txtDate.getText();
            String desc = txtDesc.getText();

            PromotionDto promotionDto = new PromotionDto(promotionId, code, dis, date, desc);

            try {
                boolean isSave = promotionModel.savePromotion(promotionDto);
                if (isSave) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Promotion saved successfully").show();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                e.printStackTrace();
            }
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = promotionModel.getNextId();
        lblId.setText(nextId);
    }


    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String promotionId = lblId.getText();
            String code = txtCode.getText();
            int dis = Integer.parseInt(txtDis.getText());
            String date = txtDate.getText();
            String desc = txtDesc.getText();

            PromotionDto promotionDto = new PromotionDto(promotionId, code, dis, date, desc);

            try {
                boolean isUpdate = promotionModel.updatePromotion(promotionDto);
                if (isUpdate) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Promotion updated successfully").show();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                e.printStackTrace();
            }
        }
    }

    public void btndeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this promotion?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String promoId = lblId.getText();
                boolean isDelete = promotionModel.deletePromotion(promoId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "promotion Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "promotion Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to promotion salary").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        PromotionTM selectedItem = tblPromotion.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblId.setText(selectedItem.getPromotionId());
            txtCode.setText(selectedItem.getCode());
            txtDis.setText(String.valueOf(selectedItem.getDiscount()));
            txtDesc.setText(selectedItem.getDescription());
            txtDate.setText(selectedItem.getDate());


            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btbReport.setDisable(false);
        }
    }
    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btbReport.setDisable(true);
        btnSave.setDisable(false);

        txtCode.setText("");
        txtDis.setText("");
        txtDate.setText("");
        txtDesc.setText("");



    }

}
