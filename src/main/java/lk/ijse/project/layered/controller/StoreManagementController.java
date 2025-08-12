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
import lk.ijse.project.layered.bo.custom.OrderBO;
import lk.ijse.project.layered.bo.custom.StoreManagementBO;
import lk.ijse.project.layered.dto.StoreManagementDto;
import lk.ijse.project.layered.dto.tm.CustomerTM;
import lk.ijse.project.layered.dto.tm.StoreTM;
import lk.ijse.project.layered.model.OrderModel;
import lk.ijse.project.layered.model.StoreManagementModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StoreManagementController implements Initializable {
    public Label lblStId;
    public TextField txtOrId;
    public TextField txtCapa;

    private final String capacityPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
    public Button btnReport;

    private boolean validateInput() {
        boolean isValid = true;

        txtCapa.setStyle("-fx-text-fill: black;");

        if (!txtCapa.getText().matches(capacityPattern)) {
            txtCapa.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        return isValid;
    }

    private final StoreManagementModel storeManagementModel = new StoreManagementModel();
    private final OrderModel orderModel = new OrderModel();
    private final StoreManagementBO storeManagementBO = BOFactory.getInstance().getBO(BOType.STOREMANAGEMENT);
    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);

    public TableView <StoreTM> tblStore;
    public TableColumn <StoreTM, String> colStId;
    public TableColumn <StoreTM, String> colOrId;
    public TableColumn <StoreTM, String> colCapacity;
    public ComboBox <String>cmOrderId;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStId.setText("Store Id");
        colOrId.setText("Order Id");
        colCapacity.setText("Capacity");

        colStId.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        colOrId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        try {
            resetPage();
            loadOrderId();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed load data", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
//        ArrayList <StoreManagementDto> storeManagementDtoArrayList =storeManagementModel.getAllStore();
//        ObservableList<StoreTM> list = FXCollections.observableArrayList();
//
//        for (StoreManagementDto storeManagementDto : storeManagementDtoArrayList){
//             StoreTM storeTM = new StoreTM(
//                     storeManagementDto.getStoreId(),storeManagementDto.getOrderId(),
//                     storeManagementDto.getCapacity()
//             );
//          list.add(storeTM);
//        }
//       tblStore.setItems(list);

        tblStore.setItems(FXCollections.observableArrayList(
                storeManagementBO.getAllStores().stream().map(storeManagementDto -> new StoreTM(
                        storeManagementDto.getStoreId(),
                        storeManagementDto.getOrderId(),
                        storeManagementDto.getCapacity()
                )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            String storeId = lblStId.getText();
            String orderId = cmOrderId.getValue();
            double capacity = Double.parseDouble(txtCapa.getText());

            StoreManagementDto storeManagementDto = new StoreManagementDto(storeId, orderId, capacity);

            try {
                boolean isSave = storeManagementBO.saveStore(storeManagementDto);
                if (isSave) {
                    resetPage();

                    new Alert(Alert.AlertType.INFORMATION,"Successfully saved the store").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                e.printStackTrace();
            }
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = storeManagementBO.getNextId();
        lblStId.setText(nextId);
    }
    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextId();
        loadTableData();

        cmOrderId.setValue(null);
        txtCapa.setText("");

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnReport.setDisable(true);
        btnSave.setDisable(false);



    }

    public void btnREsetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnUpdate(ActionEvent actionEvent) {
       if (validateInput()) {
           String storeId = lblStId.getText();
           String orderId = cmOrderId.getValue();
           double capacity = Double.parseDouble(txtCapa.getText());

           StoreManagementDto storeManagementDto = new StoreManagementDto(storeId, orderId, capacity);

           try {
               boolean isUpdate = storeManagementBO.updateStore(storeManagementDto);
               if (isUpdate) {
                   resetPage();

                   new Alert(Alert.AlertType.INFORMATION,"Successfully updated the store").show();
               }else {
                   new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
               }
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
               e.printStackTrace();
           }
       }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this store?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();
        if(response.isPresent() && response.get() == ButtonType.YES){
            try {
                String storeId = lblStId.getText();
                boolean isDelete = storeManagementBO.deleteStore(storeId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "store Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "store Not Deleted").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fail to delete store").show();
                e.printStackTrace();
            }
        }
    }

    public void tblOnClick(MouseEvent mouseEvent) {
        StoreTM selectedItem = tblStore.getSelectionModel().getSelectedItem();
           if (selectedItem != null) {
               lblStId.setText(selectedItem.getStoreId());
               cmOrderId.setValue(selectedItem.getOrderId());
               txtCapa.setText(String.valueOf(selectedItem.getCapacity()));

               btnDelete.setDisable(false);
               btnUpdate.setDisable(false);
               btnReport.setDisable(false);
               btnSave.setDisable(true);

        }
    }

    private void loadOrderId() throws SQLException, ClassNotFoundException {
        List<String> list = orderBO.getAllOrderIds();
        ObservableList<String> orderIds = FXCollections.observableArrayList();
        orderIds.addAll(list);
        cmOrderId.setItems(orderIds);
    }
}
