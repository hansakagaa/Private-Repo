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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemManageFormController {
    public AnchorPane root;
    public JFXButton btnNew;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSaveOrUpdate;
    public JFXButton btnDelete;
    public TableView<ItemTM> tblItem;

    public void initialize() {
//
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
//
        initialUi();
//
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSaveOrUpdate.setText(newValue != null ? "Update" : "Save");
            btnSaveOrUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtItemCode.setText(newValue.getItemCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtUnitPrice.setText(newValue.getUnitPrice().toString());
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtItemCode.setDisable(false);
                txtDescription.setDisable(false);
                txtPackSize.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });
//
        loadAllItem();

//
        txtDescription.setOnAction(event -> txtPackSize.requestFocus());
        txtPackSize.setOnAction(event -> txtUnitPrice.requestFocus());
        txtUnitPrice.setOnAction(event -> txtQtyOnHand.requestFocus());
        txtQtyOnHand.setOnAction(event -> btnSaveOrUpdate.fire());
//
    }
//
    private void initialUi() {
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

        txtItemCode.setDisable(true);
        txtDescription.setDisable(true);
        txtPackSize.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);

        btnSaveOrUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
//
    private void loadAllItem() {
        tblItem.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            while (rst.next()) {
                tblItem.getItems().add(new ItemTM(rst.getString("itemCode"),rst.getString("description"),rst.getString("packSize"), rst.getBigDecimal("unitPrice"),rst.getInt("qtyOnHand")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//
    @FXML
    public void navigateToHome(MouseEvent event) throws IOException {
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/administrator-form.fxml"))));
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
//
    @FXML
    public void newItemOnAction(ActionEvent actionEvent) {
        txtItemCode.setDisable(false);
        txtDescription.setDisable(false);
        txtPackSize.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);

        txtItemCode.clear();
        txtItemCode.setText(generateNewId());
        txtItemCode.setEditable(false);

        txtDescription.clear();
        txtUnitPrice.clear();
        txtPackSize.clear();
        txtQtyOnHand.clear();
        txtDescription.requestFocus();

        btnSaveOrUpdate.setDisable(false);
        btnSaveOrUpdate.setText("Save");
        tblItem.getSelectionModel().clearSelection();
    }
//
    private String generateNewId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("ItemCode");
                int newItemId = Integer.parseInt(id.replace("I-", "")) + 1;
                return String.format("I-%04d", newItemId);
            } else {
                return "I-0001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblItem.getItems().isEmpty()) {
            return "I-0001";
        } else {
            String id = getLastItemId();
            int newCustomerId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I-%04d", newCustomerId);
        }
    }
//
    private String getLastItemId() {
        List<ItemTM> tempItemList = new ArrayList<>(tblItem.getItems());
        Collections.sort(tempItemList);
        return tempItemList.get(tempItemList.size() - 1).getItemCode();
    }
//
    @FXML
    public void ItemSaveOrUpdateOnAction(ActionEvent actionEvent) {
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);

        if (!description.matches("^[A-z0-9 ]{5,50}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!unitPrice.toString().matches("^([0-9]+)$|([0-9]+[.][0-9]{2})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!packSize.matches("^[A-z0-9 ]{5,20}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid pack size").show();
            txtQtyOnHand.requestFocus();
            return;
        }else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        if (btnSaveOrUpdate.getText().equalsIgnoreCase("save")) {
            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pStm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?)");
                pStm.setString(1, code);
                pStm.setString(2, description);
                pStm.setString(3, packSize);
                pStm.setBigDecimal(4, unitPrice);
                pStm.setInt(5, qtyOnHand);
                pStm.executeUpdate();
                tblItem.getItems().add(new ItemTM(code, description, packSize, unitPrice, qtyOnHand));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                /*Update Item*/
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pStm = connection.prepareStatement("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?");
                pStm.setString(1, description);
                pStm.setString(2, packSize);
                pStm.setBigDecimal(3, unitPrice);
                pStm.setInt(4, qtyOnHand);
                pStm.setString(5, code);
                pStm.executeUpdate();

                ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setPackSize(packSize);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setQtyOnHand(qtyOnHand);

                tblItem.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnNew.fire();
    }
//
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("SELECT itemCode FROM Item WHERE itemCode=?");
        pStm.setString(1, code);
        return pStm.executeQuery().next();
    }
//
    @FXML
    public void ItemRemoveOnAction(ActionEvent actionEvent) {
        String code = tblItem.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStm = connection.prepareStatement("DELETE FROM Item WHERE itemCode=?");
            pStm.setString(1, code);
            pStm.executeUpdate();

            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            initialUi();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
