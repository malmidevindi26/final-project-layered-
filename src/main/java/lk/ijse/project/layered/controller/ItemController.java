package lk.ijse.project.layered.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.project.layered.bo.BOFactory;
import lk.ijse.project.layered.bo.BOType;
import lk.ijse.project.layered.bo.custom.InventoryBO;
import lk.ijse.project.layered.bo.custom.ItemBO;
import lk.ijse.project.layered.bo.custom.OrderBO;
import lk.ijse.project.layered.dto.InventoryDto;
import lk.ijse.project.layered.dto.ItemDto;
import lk.ijse.project.layered.dto.OrderDto;
import lk.ijse.project.layered.dto.OrderItemDto;
import lk.ijse.project.layered.dto.tm.CartTM;
import lk.ijse.project.layered.model.CustomerModel;
import lk.ijse.project.layered.model.InventoryModel;
import lk.ijse.project.layered.model.ItemModel;
import lk.ijse.project.layered.model.OrderModel;
import java.sql.Date;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    public ComboBox<String> cmOrderId;
    public Label orderDate;
    public Label lblCustomerId;
    public Label lblCategory;
    public ComboBox<String> cmbItemId;
    public Label lblItemName;
    public Label lblItemQty;
    public Label lblItemPrice;
    public TextField txtAddToCartQty;
    public TableView<CartTM> tblCart;
    public TableColumn<CartTM, String> colItemId;
    public TableColumn<CartTM, String> colName;
    public TableColumn<CartTM, Double> colQuantity;
    public TableColumn<CartTM, Double> colPrice;
    public TableColumn<CartTM, Double> colTotal;
    public TableColumn<CartTM, Button> colAction;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();
    private final InventoryModel inventoryModel = new InventoryModel();
    private final ObservableList<CartTM> cartData = FXCollections.observableArrayList();

    private final ItemBO itemBO = BOFactory.getInstance().getBO(BOType.ITEM);
    private final InventoryBO inventoryBO = BOFactory.getInstance().getBO(BOType.INVENTORY);
    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
    }

    public void cmbItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
         String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
         InventoryDto inventoryDto = inventoryBO.findById(selectedItemId);

         if(inventoryDto != null){
             lblItemName.setText(inventoryDto.getName());
             lblItemQty.setText(String.valueOf(inventoryDto.getQuantity()));
             lblItemPrice.setText(String.valueOf(inventoryDto.getPrice()));
         }else {
             lblItemName.setText("");
             lblItemQty.setText("");
             lblItemPrice.setText("");
         }


    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String selectedItemId = cmbItemId.getValue();
        String cartQtyString =txtAddToCartQty.getText();

        if(selectedItemId ==null){
            new Alert(Alert.AlertType.WARNING,"Please select an item id").show();
        }
        if(!cartQtyString.matches("^[0-9]+$")){
            new Alert(Alert.AlertType.WARNING,"Please enter a valid quantity").show();
            return;
        }
        double cartQty = Double.parseDouble(cartQtyString);
        double itemStockQty = Double.parseDouble(lblItemQty.getText());

        if(itemStockQty < cartQty){
            new Alert(Alert.AlertType.WARNING,"Please enter a valid quantity").show();
            return;
        }
        String itemName = lblItemName.getText();
        double itemPrice = Double.parseDouble(lblItemPrice.getText());
        double total = itemPrice * cartQty;

        for(CartTM cartTM: cartData){
            if(cartTM.getItemId().equals(selectedItemId)){
                int newQty = (int) (cartTM.getCartQty() + cartQty);
                if(itemStockQty < newQty){
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Not enough item quantity..!"
                    ).show();
                    return;
                }
                cartTM.setCartQty(newQty);
                cartTM.setTotal(newQty * itemPrice);

                txtAddToCartQty.setText("");
                tblCart.refresh();
                return;
            }
        }

        Button removeBtn = new Button("Remove");

        CartTM cartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                itemPrice,
                total,
                removeBtn
        );
        removeBtn.setOnAction(action -> {
            cartData.remove(cartTM);
            tblCart.refresh();
        });
        txtAddToCartQty.setText("");
        cartData.add(cartTM);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        if (tblCart.getItems().isEmpty()) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please add items to cart..!"
            ).show();
            return;
        }
//        if (cmOrderId.getValue().isEmpty()) {
//            new Alert(
//                    Alert.AlertType.WARNING,
//                    "Please select order for place order..!"
//            ).show();
//            return;
//        }
        if (cmOrderId.getValue() == null || cmOrderId.getValue().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select order for place order..!").show();
            return;
        }

        String selectedOrderId = cmOrderId.getValue();
        String customerId = lblCustomerId.getText();
        LocalDate localDate = LocalDate.parse(orderDate.getText());
        Date date = java.sql.Date.valueOf(localDate);


        ArrayList <OrderItemDto> cartList = new ArrayList<>();

        for (CartTM cartTM : cartData) {
            OrderItemDto orderItemDto = new OrderItemDto(
                    cartTM.getItemId(),
                    selectedOrderId,
                    cartTM.getCartQty(),
                    cartTM.getUnitPrice()
            );

            cartList.add(orderItemDto);
        }
        ItemDto itemDto = new ItemDto(
                selectedOrderId,
                customerId,
                date,
                cartList
        );
        try {
           // boolean isPlaced = itemModel.placeOrder(itemDto);
            boolean isPlaced = itemBO.placeOrder(itemDto);

            if(isPlaced){
                new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                cartData.clear();
                tblCart.refresh();
                resetPage();
            }else {
                new Alert(Alert.AlertType.WARNING, "Fail to place order..!").show();

            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to place order..!").show();
        }

    }


    public void cmOrderIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedItemId = cmOrderId.getSelectionModel().getSelectedItem();
        OrderDto orderDto = orderBO.findById(selectedItemId);

        if(orderDto != null){
            lblCustomerId.setText(orderDto.getCustomId());
            lblCategory.setText(orderDto.getClothCategory());
        }else {
            lblCustomerId.setText("");
            lblCategory.setText("");
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIdsList = inventoryBO.getAllItemIds();
        ObservableList<String> itemIds = FXCollections.observableArrayList();
        itemIds.addAll(itemIdsList);
        cmbItemId.setItems(itemIds);
    }

    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        List<String> customerIdsList = orderBO.getAllOrderIds();
        ObservableList<String> customerIds = FXCollections.observableArrayList(customerIdsList);
        customerIds.addAll(customerIdsList);
       // System.out.println(customerIdsList.getFirst());
        cmOrderId.setItems(customerIds);

        }



    private void resetPage() throws SQLException, ClassNotFoundException {
           cmOrderId.setValue(null);
           orderDate.setText(LocalDate.now().toString());
           cmbItemId.setValue(null);
           lblCategory.setText(null);
           lblCustomerId.setText(null);
           lblItemName.setText(null);
           lblItemQty.setText(null);
           lblItemPrice.setText(null);
           txtAddToCartQty.setText(null);


           loadItemIds();
           loadOrderIds();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        tblCart.setItems(cartData);

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load data").show();
        }
    }
}
