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
import view.tm.CustomerTM;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerManageFormController {
    public AnchorPane root;
    public JFXButton btnNew;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTitle;
    public JFXTextField txtPostalCode;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXButton btnSaveOrUpdate;
    public JFXButton btnDelete;
    public TableView<CustomerTM> tblCustomer;

    public void initialize() throws SQLException, ClassNotFoundException {
//
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("city"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("province"));
        tblCustomer.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("postalCode"));
//
        initialUi();
//
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSaveOrUpdate.setText(newValue != null ? "Update" : "Save");
            btnSaveOrUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtTitle.setText(newValue.getTitle());
                txtCity.setText(newValue.getCity());
                txtProvince.setText(newValue.getProvince());
                txtPostalCode.setText(newValue.getPostalCode());

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtTitle.setDisable(false);
                txtCity.setDisable(false);
                txtProvince.setDisable(false);
                txtPostalCode.setDisable(false);

            }
        });
//
        loadAllCustomer();
//
        txtName.setOnAction(event -> txtAddress.requestFocus());
        txtAddress.setOnAction(event -> txtTitle.requestFocus());
        txtTitle.setOnAction(event -> txtPostalCode.requestFocus());
        txtPostalCode.setOnAction(event -> txtCity.requestFocus());
        txtCity.setOnAction(event -> txtProvince.requestFocus());
        txtProvince.setOnAction(event -> btnSaveOrUpdate.fire());
//
    }
//
    private void initialUi() {
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtTitle.setDisable(true);
        txtPostalCode.setDisable(true);
        txtCity.setDisable(true);
        txtProvince.setDisable(true);
        btnSaveOrUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
//
    private void loadAllCustomer() throws SQLException, ClassNotFoundException {
        tblCustomer.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
            while (rst.next()) {
                tblCustomer.getItems().add(new CustomerTM(rst.getString("id"),rst.getString("title"),rst.getString("name"),rst.getString("address"),rst.getString("city"),rst.getString("province"),rst.getString("postalCode")));
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

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
    public void newCustomerOnAction(ActionEvent actionEvent) {
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtTitle.setDisable(false);
        txtCity.setDisable(false);
        txtProvince.setDisable(false);
        txtPostalCode.setDisable(false);

        txtId.clear();
        txtId.setText(generateNewId());
        txtName.clear();
        txtAddress.clear();
        txtTitle.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtCity.clear();

        txtName.requestFocus();

        btnSaveOrUpdate.setDisable(false);
        btnSaveOrUpdate.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();
    }
//
    private String generateNewId() {
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

        if (tblCustomer.getItems().isEmpty()) {
            return "C-0001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C-%04d", newCustomerId);
        }
    }
//
    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getId();
    }
//
    @FXML
    public void customerSaveOrUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String title = txtTitle.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        if (!name.matches("^[A-z ]{3,6}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name. (3-6) characters only.").show();
            txtName.requestFocus();txtName.selectAll();
            return;
        } else if (!address.matches("^[A-z \\d]{5,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at (5-30) A-z characters only").show();
            txtAddress.requestFocus();txtAddress.selectAll();
            return;
        } else if (!title.matches("^[A-z \\d]{3,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Title should be at (3-5) characters only").show();
            txtTitle.requestFocus();txtTitle.selectAll();
            return;
        } else if (!city.matches("^[A-z \\d]{5,20}$")) {
            new Alert(Alert.AlertType.ERROR, "City should be at (5-20) A-z characters only").show();
            txtCity.requestFocus();txtCity.selectAll();
            return;
        } else if (!province.matches("^[A-z \\d]{5,20}$")) {
            new Alert(Alert.AlertType.ERROR, "Province should be at (5-20) A-z characters only").show();
            txtProvince.requestFocus();txtProvince.selectAll();
            return;
        } else if (!postalCode.matches("^[A-z \\d]{5,9}$")) {
            new Alert(Alert.AlertType.ERROR, "Postal Code should be at (5-9) characters only").show();
            txtPostalCode.requestFocus();txtPostalCode.selectAll();
            return;
        }

        if (btnSaveOrUpdate.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pStm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)");
                pStm.setString(1, id);
                pStm.setString(2, title);
                pStm.setString(3, name);
                pStm.setString(4, address);
                pStm.setString(5, city);
                pStm.setString(6, province);
                pStm.setString(7, postalCode);
                pStm.executeUpdate();

                tblCustomer.getItems().add(new CustomerTM(id,title,name,address,city,province,postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            /*Update customer*/
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pStm = connection.prepareStatement("UPDATE Customer SET title=?, name=?, address=?, city=?,  province=?, postalCode=? WHERE id=?");
                pStm.setString(1, title);
                pStm.setString(2, name);
                pStm.setString(3, address);
                pStm.setString(4, city);
                pStm.setString(5, province);
                pStm.setString(6, postalCode);
                pStm.setString(7, id);
                pStm.executeUpdate();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectedCustomer.setTitle(title);
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setProvince(province);
            selectedCustomer.setPostalCode(postalCode);
            tblCustomer.refresh();
        }
        btnNew.fire();
    }
//
    @FXML
    public void customerRemoveOnAction(ActionEvent actionEvent) {
        /*Delete Customer*/
        String id = tblCustomer.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
            pstm.setString(1, id);
            pstm.executeUpdate();

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            initialUi();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pStm.setString(1, id);
        return pStm.executeQuery().next();
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
