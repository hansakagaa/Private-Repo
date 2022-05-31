package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import view.tm.CartTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceOrderFormController {
    public AnchorPane root;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTitle;
    public JFXTextField txtPostalCode;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtTotal;
    public JFXTextField txtDiscount;
    public JFXTextField txtCash;
    public JFXTextField txtBalance;
    public JFXTextField txtOrderQty;
    public ComboBox<String> cmbCustomerId;
    public ComboBox<String> cmbItemCode;
    public Label lblDate;
    public Label lblOrderId;
    public JFXButton btnCancel;
    public JFXButton btnSave;
    public ImageView imgAddCart;
    public ImageView imgNewCustomer;
    public TableView<CartTM> tblList;
    private String orderId;

    public void initialize(){
//
        tblList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblList.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblList.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<CartTM, Button> lastCol = (TableColumn<CartTM, Button>) tblList.getColumns().get(6);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblList.getItems().remove(param.getValue());
                tblList.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
//
        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);
        lblDate.setText(LocalDate.now().toString());
        cmbCustomerId.setDisable(false);
        txtId.setDisable(true);
        btnSave.setDisable(true);

        txtName.setFocusTraversable(false);
        txtAddress.setFocusTraversable(false);
        txtTitle.setFocusTraversable(false);
        txtCity.setFocusTraversable(false);
        txtPostalCode.setFocusTraversable(false);
        txtProvince.setFocusTraversable(false);
        txtDescription.setFocusTraversable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtOrderQty.setEditable(true);

        txtOrderQty.setOnAction(event -> navigateToAddCart(null));

        imgAddCart.setDisable(true);
//
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    Connection connection = DBConnection.getInstance().getConnection();
                    try {
                        if (existCustomer(newValue + "")) {
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }

                        PreparedStatement pStm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
                        pStm.setString(1, newValue + "");
                        ResultSet rst = pStm.executeQuery();
                        rst.next();
                        CustomerDTO customerDTO = new CustomerDTO(newValue +"", rst.getString("title"),  rst.getString("name"), rst.getString("address"), rst.getString("city"), rst.getString("province"), rst.getString("postalCode"));

                        txtName.setText(customerDTO.getName());
                        txtName.setEditable(false);
                        txtAddress.setText(customerDTO.getAddress());
                        txtAddress.setEditable(false);
                        txtTitle.setText(customerDTO.getTitle());
                        txtTitle.setEditable(false);
                        txtPostalCode.setText(customerDTO.getPostalCode());
                        txtPostalCode.setEditable(false);
                        txtCity.setText(customerDTO.getCity());
                        txtCity.setEditable(false);
                        txtProvince.setText(customerDTO.getProvince());
                        txtProvince.setEditable(false);

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtName.clear();
                txtAddress.clear();
                txtTitle.clear();
                txtCity.clear();
                txtPostalCode.clear();
                txtProvince.clear();
            }
        });
//
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtOrderQty.setEditable(newItemCode != null);
            imgAddCart.setDisable(newItemCode == null);

            if (newItemCode != null) {
                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
                    }
                    Connection connection = DBConnection.getInstance().getConnection();
                    PreparedStatement pStm = connection.prepareStatement("SELECT * FROM Item WHERE itemCode=?");
                    pStm.setString(1, newItemCode + "");
                    ResultSet rst = pStm.executeQuery();
                    rst.next();
                    ItemDTO item = new ItemDTO(newItemCode + "", rst.getString("description"), rst.getString("packSize"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));

                    txtDescription.setText(item.getDescription());
                    txtPackSize.setText(item.getPackSize());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());
                    txtQtyOnHand.setText(item.getQtyOnHand()+"");

                    Optional<CartTM> optOrderDetail = tblList.getItems().stream().filter(detail -> detail.getItemCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getOrderQty() : item.getQtyOnHand()) + "");

                    txtOrderQty.requestFocus();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
                txtOrderQty.clear();
            }
        });
//
        tblList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getOrderQty() + "");
                txtOrderQty.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtOrderQty.clear();
            }
        });
//
        loadAllCustomerIds();
        loadAllItemCodes();
//
        if (cmbCustomerId.getItems().isEmpty()){
            navigateToNewCustomer(null);
        }
//
        txtId.setOnAction(event -> txtName.requestFocus());
        txtName.setOnAction(event -> txtAddress.requestFocus());
        txtAddress.setOnAction(event -> txtTitle.requestFocus());
        txtTitle.setOnAction(event -> txtPostalCode.requestFocus());
        txtPostalCode.setOnAction(event -> txtCity.requestFocus());
        txtCity.setOnAction(event -> txtProvince.requestFocus());
//
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
    private void loadAllItemCodes() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            while (rst.next()) {
                cmbItemCode.getItems().add(rst.getString("itemCode"));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("SELECT itemCode FROM Item WHERE itemCode=?");
        pStm.setString(1, code);
        return pStm.executeQuery().next();
    }
//
    private boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pStm.setString(1, id);
        return !pStm.executeQuery().next();
    }
//
    private String generateNewOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT orderID FROM Orders ORDER BY orderID DESC LIMIT 1;");

            return rst.next() ? String.format("OI-%03d", (Integer.parseInt(rst.getString("orderID").replace("OI-", "")) + 1)) : "OI-001";
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OI-001";
    }
//
    private void enableOrDisablePlaceOrderButton() {
        btnSave.setDisable(!((cmbCustomerId.getSelectionModel().getSelectedItem() != null || txtId.getText() != null) && !tblList.getItems().isEmpty()));

    }
//
    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (CartTM detail : tblList.getItems()) {
            total = total.add(detail.getTotal());
        }
        txtTotal.setText(total+" /=");
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
    public void navigateToNewCustomer(MouseEvent event) {
        cmbCustomerId.setDisable(true);
        cmbCustomerId.setVisible(false);
        txtId.setDisable(false);
        txtId.setText(generateNewCustomerId());

        txtName.requestFocus();
        txtName.setEditable(true);
        txtName.clear();
        txtAddress.setEditable(true);
        txtAddress.clear();
        txtTitle.setEditable(true);
        txtTitle.clear();
        txtCity.setEditable(true);
        txtCity.clear();
        txtProvince.setEditable(true);
        txtProvince.clear();
        txtPostalCode.setEditable(true);
        txtPostalCode.clear();
    }
//
    private String generateNewCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("id");
                int newCustomerId = Integer.parseInt(id.replace("C-", "")) + 1;
                return String.format("C-%04d", newCustomerId);
            } else {
                return "C-0001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (cmbCustomerId.getItems().isEmpty()) {
            return "C-0001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C-%04d", newCustomerId);
        }
    }
//
    private String getLastCustomerId() {
        List<String> tempCustomersList = new ArrayList<>(cmbCustomerId.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1);
    }
//
    @FXML
    public void navigateToAddCart(MouseEvent event) {
        if (!txtOrderQty.getText().matches("\\d+") || Integer.parseInt(txtOrderQty.getText()) <= 0 ||
                Integer.parseInt(txtOrderQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtOrderQty.requestFocus();
            txtOrderQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtOrderQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblList.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

        if (exists) {
            CartTM cartTM = tblList.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();

            cartTM.setOrderQty(qty);
            total = new BigDecimal(cartTM.getOrderQty()).multiply(unitPrice).setScale(2);
            cartTM.setTotal(total);
            tblList.getSelectionModel().clearSelection();

            tblList.refresh();
        } else {
            tblList.getItems().add(new CartTM(itemCode, description, packSize, qty, unitPrice, total));
        }
        cmbItemCode.setDisable(false);
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }
//
    @FXML
    public void cancelOrderOnAction(ActionEvent actionEvent) throws IOException {
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblList.getItems().clear();
        txtId.setDisable(true);
        cmbCustomerId.setDisable(false);
        cmbCustomerId.setVisible(true);
        txtOrderQty.clear();
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTitle.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        calculateTotal();
    }
//
    @FXML
    public void saveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        CustomerDTO customerDTO = new CustomerDTO(txtId.getText(), txtTitle.getText(), txtName.getText(), txtAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText());
        String id = cmbCustomerId.getValue();
        /*if customer id already exist*/
        if (existCustomer(id)){
            if (saveCustomer(customerDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer has been save successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer has been save Unsuccessfully").show();
                return;
            }
        }

        if (id == null){
            id = customerDTO.getId();
        }

        boolean saveOrder = saveOrder(orderId, LocalDate.now(), id,
                tblList.getItems().stream().map(tm -> new OrderDetailsDTO(orderId, tm.getItemCode(), tm.getOrderQty(), tm.getUnitPrice()/**/)).collect(Collectors.toList()));

        if (saveOrder) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }
        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblList.getItems().clear();
        txtId.setDisable(true);
        cmbCustomerId.setDisable(false);
        cmbCustomerId.setVisible(true);
        txtOrderQty.clear();
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTitle.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        calculateTotal();
    }
//
    private boolean saveOrder(String orderId, LocalDate date, String id, List<OrderDetailsDTO> orderDetails) {
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("SELECT orderID FROM Orders WHERE orderID=?");
            pStm.setString(1, orderId);
            /*if order id already exist*/
            if (pStm.executeQuery().next()) {
                System.out.println();
            }
            connection.setAutoCommit(false);

            pStm = connection.prepareStatement("INSERT INTO Orders VALUES (?,?,?)");
            pStm.setString(1, orderId);
            pStm.setDate(2, Date.valueOf(date));
            pStm.setString(3, id);
            if (pStm.executeUpdate() != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            pStm = connection.prepareStatement("INSERT INTO `Order Detail` VALUES (?,?,?,?)");

            for (OrderDetailsDTO detail : orderDetails) {
                pStm.setString(1, detail.getOrderID());
                pStm.setString(2, detail.getItemCode());
                pStm.setInt(3, detail.getOrderQty());
                pStm.setBigDecimal(4, detail.getDiscount());

                if (pStm.executeUpdate() != 1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                //Search & Update Item
                ItemDTO item = findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getOrderQty());

                PreparedStatement stm = connection.prepareStatement("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?");
                stm.setString(1, item.getDescription());
                stm.setString(2, item.getPackSize());
                stm.setBigDecimal(3, item.getUnitPrice());
                stm.setInt(4, item.getQtyOnHand());
                stm.setString(5, item.getItemCode());

                if (!(stm.executeUpdate() > 0)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
//
    private boolean saveCustomer(CustomerDTO dto)  {

        if (!dto.getName().matches("^[A-z ]{5,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name. (5-30) characters only.").show();
            txtName.requestFocus();txtName.selectAll();
            return false;
        } else if (!dto.getAddress().matches("^[A-z \\d]{5,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at (5-30) A-z characters only").show();
            txtAddress.requestFocus();txtAddress.selectAll();
            return false;
        } else if (!dto.getTitle().matches("^[A-z \\d]{3,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Title should be at (3-5) characters only").show();
            txtTitle.requestFocus();txtTitle.selectAll();
            return false;
        } else if (!dto.getCity().matches("^[A-z \\d]{5,20}$")) {
            new Alert(Alert.AlertType.ERROR, "City should be at (5-20) A-z characters only").show();
            txtCity.requestFocus();txtCity.selectAll();
            return false;
        } else if (!dto.getProvince().matches("^[A-z \\d]{5,20}$")) {
            new Alert(Alert.AlertType.ERROR, "Province should be at (5-20) A-z characters only").show();
            txtProvince.requestFocus();txtProvince.selectAll();
            return false;
        } else if (!dto.getPostalCode().matches("^[A-z \\d]{5,9}$")) {
            new Alert(Alert.AlertType.ERROR, "Postal Code should be at (5-9) characters only").show();
            txtPostalCode.requestFocus();txtPostalCode.selectAll();
            return false;
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)");
            pStm.setString(1, dto.getId());
            pStm.setString(2, dto.getTitle());
            pStm.setString(3, dto.getName());
            pStm.setString(4, dto.getAddress());
            pStm.setString(5, dto.getCity());
            pStm.setString(6, dto.getProvince());
            pStm.setString(7, dto.getPostalCode());

            if (pStm.executeUpdate() != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
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
            txtOrderQty.requestFocus();
            txtOrderQty.selectAll();
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
        BigDecimal total = new BigDecimal(0);

        for (CartTM detail : tblList.getItems()) {
            total = total.add(detail.getTotal());
        }
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
