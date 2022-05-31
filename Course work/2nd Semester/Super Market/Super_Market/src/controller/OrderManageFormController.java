package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDetailsDTO;
import view.tm.ItemTM;
import view.tm.OrderTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderManageFormController {
    public AnchorPane root;
    public Label lblOrderId;
    public Label lblItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtTotal;
    public JFXTextField txtOrderQty;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtPresTotal;
    public JFXTextField txtNewTotal;
    public JFXTextField txtCash;
    public JFXTextField txtBalance;
    public JFXTextField txtOrderId;
    public JFXButton btnCancel;
    public JFXButton btnDelete;
    public JFXButton btnRemove;
    public JFXButton btnAdd;
    public JFXButton btnSave;
    public ComboBox<String> cmbCustomerId;
    public TableView<OrderTM> tblOrderId;
    public TableView<ItemTM> tblOrderDetails;

    public void  initialize(){
//
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("orderQty"));

        tblOrderId.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderID"));
//
        initialItem(true);
        tblOrderId.setDisable(true);
        tblOrderDetails.setDisable(true);
        lblItemCode.setText("");
        lblOrderId.setText("");
        btnDelete.setDisable(true);
//
        loadAllCustomerIds();
//
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, customerId) -> {
            //  enableOrDisableSaveButton();
            if (customerId != null) {
                tblOrderId.setDisable(false);
                tblOrderDetails.setDisable(true);
                tblOrderDetails.getItems().clear();
                initialItem();
                initialItem(true);
                try {
                    /*Search Customer*/
                    Connection connection = DBConnection.getInstance().getConnection();

                    if (existCustomer(customerId + "")) {
                        new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + customerId + "").show();
                    }

                    PreparedStatement pStm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
                    pStm.setString(1, customerId + "");
                    ResultSet rst = pStm.executeQuery();
                    rst.next();
                    CustomerDTO customerDTO = new CustomerDTO(customerId +"", rst.getString("title"),  rst.getString("name"), rst.getString("address"), rst.getString("city"), rst.getString("province"), rst.getString("postalCode"));

                    txtName.setText(customerDTO.getName());
                    txtAddress.setText(customerDTO.getAddress());
                    loadAllOrders(customerId + "");

                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + customerId + "" + e).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                tblOrderId.setDisable(true);
                tblOrderDetails.getItems().clear();
                tblOrderDetails.setDisable(true);
                txtName.clear();
                txtAddress.clear();
                initialItem(true);
                initialItem();
            }
        });
//
        tblOrderId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, orders) -> {
            if (orders != null) {
                initialItem();
                initialItem(true);
                tblOrderDetails.setDisable(false);
                lblOrderId.setText(orders.getOrderID());
                txtOrderId.setText(orders.getOrderID());
                loadAllItems(orders.getOrderID());
                btnDelete.setDisable(false);
                txtPresTotal.setText(String.valueOf(calculateAllTotal()));
            }else {
                tblOrderDetails.setDisable(true);
                initialItem();
                initialItem(true);
                btnDelete.setDisable(true);
                txtNewTotal.clear();
                txtPresTotal.clear();
                txtCash.clear();
                txtBalance.clear();
            }
        });
//
        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, item) -> {
            if(item != null){
                initialItem(false);
                lblItemCode.setText(item.getItemCode());
                txtDescription.setText(item.getDescription());
                txtPackSize.setText(item.getPackSize());
                txtUnitPrice.setText(item.getUnitPrice().toString());
                txtQtyOnHand.setText(item.getQtyOnHand()+"");
                txtOrderQty.setText(item.getOrderQty()+"");
                calculateItemTotal();

            }else {
                initialItem(true);
            }
        });
//
        txtOrderId.setOnAction(event -> setOrder());
        txtOrderQty.setOnAction(event -> btnAdd.fire());

    }
//
    private void initialItem(boolean b){
        txtDescription.setDisable(b);
        txtPackSize.setDisable(b);
        txtUnitPrice.setDisable(b);
        txtQtyOnHand.setDisable(b);
        txtTotal.setDisable(b);
        txtOrderQty.setDisable(b);
        btnRemove.setDisable(b);
        btnAdd.setDisable(true);
    }
//
    private void initialItem(){
        lblItemCode.setText("");
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtTotal.clear();
        txtOrderQty.clear();
    }
//
    private void clearUi(){
        cmbCustomerId.getSelectionModel().clearSelection();
        txtName.clear();txtAddress.clear();
        tblOrderId.getItems().clear();
        txtOrderId.clear();lblOrderId.setText("");
        tblOrderDetails.getItems().clear();
        initialItem();
        txtNewTotal.clear();
        txtPresTotal.clear();
        txtCash.clear();
        txtBalance.clear();
    }
//
    private void calculateItemTotal() {
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtOrderQty.getText());
        BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(qty));
        txtTotal.setText(total+"");
    }
//
    private BigDecimal calculateAllTotal(){
        BigDecimal total = new BigDecimal(0);
        for (ItemTM detail : tblOrderDetails.getItems()) {
            total = total.add(BigDecimal.valueOf(detail.getOrderQty()).multiply(detail.getUnitPrice()));
        }
        return total;
    }
//
    private void loadAllItems(String orderID) {
        tblOrderDetails.getItems().clear();
        List<OrderDetailsDTO> dto = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("SELECT * FROM `Order Detail` WHERE orderID=?");
            pStm.setString(1, orderID);
            ResultSet rst = pStm.executeQuery();
            while (rst.next()) {
                dto.add(new OrderDetailsDTO(orderID, rst.getString("itemCode"), rst.getInt("OrderQty"), rst.getBigDecimal("discount")));
            }

            for (OrderDetailsDTO detailsDTO : dto) {
                pStm = connection.prepareStatement("SELECT * FROM Item WHERE itemCode=?");
                pStm.setString(1, detailsDTO.getItemCode());
                rst = pStm.executeQuery();
                while (rst.next()) {
                    tblOrderDetails.getItems().add(new ItemTM(detailsDTO.getItemCode(), rst.getString("description"), rst.getString("packSize"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"), detailsDTO.getOrderQty()));
                }
            }
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, ""+ e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, " "+e.getMessage()).show();
        }
    }
//
    private void loadAllOrders(String id){
        tblOrderId.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("SELECT orderId FROM Orders WHERE cstID=?");
            pStm.setString(1, id);
            ResultSet rst = pStm.executeQuery();
            while (rst.next()) {
                tblOrderId.getItems().add(new OrderTM(rst.getString("orderID")));
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
//
    private void loadAllCustomerIds() {
    try {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

        while (rst.next()) {
            cmbCustomerId.getItems().add(rst.getString("id"));
        }

    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}
//
    private boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pStm.setString(1, id);
        return !pStm.executeQuery().next();
    }
//
    private void setOrder() {
        if (!txtOrderId.getText().matches("^(OI-)[0-9]{3}$")) {
            txtOrderId.requestFocus();
            txtOrderId.selectAll();
            return;
        }else {
            loadAllItems(txtOrderId.getText());
        }
        lblItemCode.setText("");
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtTotal.clear();
        txtOrderQty.clear();
    }
//
    @FXML
    public void navigateToHome(MouseEvent event) throws IOException {
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/cashier-form.fxml"))));
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
//
    @FXML
    public void deleteOrderOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("DELETE FROM Orders WHERE orderID=?");
            pStm.setString(1, lblOrderId.getText());
            pStm.executeUpdate();

            tblOrderId.getItems().remove(tblOrderId.getSelectionModel().getSelectedItem());
            tblOrderId.getSelectionModel().clearSelection();

            lblOrderId.setText("");
            tblOrderDetails.getItems().clear();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + lblOrderId.getText()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//
    @FXML
    public void itemRemoveOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("DELETE FROM `Order Detail` WHERE itemCode=?");
            pStm.setString(1, lblItemCode.getText());
            pStm.executeUpdate();

            tblOrderDetails.getItems().remove(tblOrderDetails.getSelectionModel().getSelectedItem());
            tblOrderDetails.getSelectionModel().clearSelection();

            initialItem();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + lblItemCode.getText()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//
    @FXML
    public void itemAddOnAction(ActionEvent actionEvent) {

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(lblItemCode.getText()));

        if (exists) {
            ItemTM itemTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(lblItemCode.getText())).findFirst().get();
            itemTM.setOrderQty(Integer.parseInt(txtOrderQty.getText()));
            tblOrderDetails.getSelectionModel().clearSelection();
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new ItemTM(lblItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText()), Integer.parseInt(txtOrderQty.getText())));
        }

        txtNewTotal.setText(String.valueOf(calculateAllTotal()));
        initialItem();
    }
//
    @FXML
    public void cancelOrderEditOnAction(ActionEvent actionEvent) {
        clearUi();
    }
//
    @FXML
    public void saveOrderEditOnAction(ActionEvent actionEvent) {
        boolean saveOrderDetails = saveOrderDetails(
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailsDTO(lblOrderId.getText(), tm.getItemCode(), tm.getOrderQty(), tm.getUnitPrice()/**/)).collect(Collectors.toList())
        );
        if (saveOrderDetails) {
            new Alert(Alert.AlertType.INFORMATION, "Order Update has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order Update has not been placed successfully").show();
        }
        clearUi();
    }

    private boolean saveOrderDetails(List<OrderDetailsDTO> details) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            for (OrderDetailsDTO detail : details) {

                PreparedStatement stm = connection.prepareStatement("UPDATE `Order Detail` SET orderQTY=? WHERE itemCode=?");
                stm.setInt(1,detail.getOrderQty());
                stm.setString(2,detail.getItemCode());

                if (!(stm.executeUpdate() > 0)) {
                    return false;
                }


                ItemDTO item = findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getOrderQty());

                stm = connection.prepareStatement("UPDATE Item SET qtyOnHand=? WHERE itemCode=?");
                stm.setInt(1, item.getQtyOnHand());
                stm.setString(2, item.getItemCode());

                if (!(stm.executeUpdate() > 0)) {
                    return false;
                }
            }
            return true;
        } catch (SQLIntegrityConstraintViolationException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR," "+ e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,""+ e.getMessage()).show();
        }
        return false;
    }
//
    private ItemDTO findItem(String itemCode) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("SELECT * FROM Item WHERE itemCode=?");
            pStm.setString(1, itemCode);
            ResultSet rst = pStm.executeQuery();
            rst.next();
            return new ItemDTO(itemCode, rst.getString("description"), rst.getString("packSize"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + itemCode, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
//
    @FXML
    public void orderQtyKeyReleased(KeyEvent keyEvent) {
        if (!txtOrderQty.getText().matches("\\d+") || Integer.parseInt(txtOrderQty.getText()) <= 0 ||
                Integer.parseInt(txtOrderQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            btnAdd.setDisable(true);
            txtOrderQty.requestFocus();
            txtOrderQty.selectAll();
        }else {
            btnAdd.setDisable(false);
            calculateItemTotal();
        }
    }
//
    @FXML
    public void cashBalanceKeyReleased(KeyEvent keyEvent) {
        if (!txtCash.getText().matches("\\d+") || Integer.parseInt(txtCash.getText()) <= 0) {
            txtCash.requestFocus();
            txtCash.selectAll();
            return;
        }
        BigDecimal cash = new BigDecimal(txtCash.getText());
        BigDecimal total = new BigDecimal(txtNewTotal.getText());
        BigDecimal balance = cash.subtract(total);
        txtBalance.setText(String.valueOf(balance));
    }
//
    @FXML
    public void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.WHITE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }
//
    @FXML
    public void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }
}
