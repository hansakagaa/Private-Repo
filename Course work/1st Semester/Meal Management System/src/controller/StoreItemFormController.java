package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.StoreItem;
import model.Supplier;
import model.SupplyDetails;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class StoreItemFormController {
    public ComboBox<String> cmbSupId;
    public JFXTextField txtSupName;
    public JFXTextField txtSupContact;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtItemQty;
    public ComboBox<String> cmbItemCode;
    public JFXTextField txtTotalPrice;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public JFXTextField txtSupAddress;
    public Button btnSave;
    public JFXTextField txtSupId;
    public Label lblDate;
    public AnchorPane SupplierParent;
    public AnchorPane ItemParent;

    String supplierId = "";

    public void initialize(){

        btnEdit.setDisable(true);
        btnAdd.setDisable(true);
        txtSupId.setDisable(true);

        loadDate();
        try {
            setIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbSupId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSupIds(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemCode(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void setItemCode(String itemCode) throws SQLException, ClassNotFoundException {
        StoreItem storeItem = new StoreItemController().getStoreItem(itemCode);
        if (storeItem!=null){
            txtItemDescription.setText(storeItem.getsItemDescription());
            txtItemUnitPrice.setText(String.valueOf(storeItem.getsItemUnitPrice()));
        }
    }

    private void setIds() throws SQLException, ClassNotFoundException {
        List<String> itemCode = new ItemController().getItemCode();
        cmbItemCode.getItems().addAll(itemCode);

        List<String> supIds = new SupplierController().getSupplierIds();
        cmbSupId.getItems().addAll(supIds);
    }

    private void setSupIds(String supIds) throws SQLException, ClassNotFoundException {
       Supplier supplier = new SupplierController().getSupplier(supIds);
         if (supplier!=null){
            txtSupName.setText(supplier.getSupName());
            txtSupContact.setText(supplier.getSupContact());
            txtSupAddress.setText(supplier.getSupAddress());
            supplierId=supIds;
        }
    }

    public void editedSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (new SupplierController().updateSupplier(
                new Supplier(cmbSupId.getValue(), txtSupName.getText(), txtSupContact.getText(), txtSupAddress.getText())
        )){
            supplierId=cmbSupId.getValue();
            txtSupName.setStyle("-fx-border-color: aqua");
            txtSupAddress.setStyle("-fx-border-color: aqua");
            txtSupContact.setStyle("-fx-border-color: aqua");
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Are you sure there is a new Supplier ??",ButtonType.YES).showAndWait();
            if (new SupplierController().saveSupplier(
                    new Supplier(txtSupId.getText(), txtSupName.getText(), txtSupContact.getText(), txtSupAddress.getText())
            )){
                supplierId=txtSupId.getText();
                txtSupId.setStyle("-fx-border-color: aqua");
            }
        }
    }

    public void addStoreOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SupplyDetails supplyDetails =new SupplyDetails(
                supplierId,
                cmbItemCode.getValue(),
                Integer.parseInt(txtItemQty.getText()),
                txtItemDescription.getText(),
                Double.parseDouble(txtItemUnitPrice.getText()),
                lblDate.getText()
        );
        if (new StoreItemController().setSupplyDetails(supplyDetails)){
            SupplierParent.setStyle("-fx-border-color: aqua");
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..", ButtonType.CLOSE).show();
        }
        StoreItem tempItem = new StoreItemController().getStoreItem(cmbItemCode.getValue());
        if (tempItem!=null){
            StoreItem newItem = new StoreItem(
                    tempItem.getsItemCode(),
                    tempItem.getsItemDescription(),
                    Double.parseDouble(txtItemUnitPrice.getText()),
                    tempItem.getTotalQty()+Integer.parseInt(txtItemQty.getText()),
                    tempItem.getTotalPrice()+Double.parseDouble(txtTotalPrice.getText())
            );
            if (new StoreItemController().updateStoreItem(newItem)){
                ItemParent.setStyle("-fx-border-color: aqua");
            }else {
                new Alert(Alert.AlertType.WARNING, "Not Set Store..", ButtonType.CLOSE).show();
            }
        }
    }

    public void loadDate(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    public void supKeyReleasedOnAction(KeyEvent keyEvent) {
        Pattern namePattern = Pattern.compile("^[A-Z. ]+[A-z ]{3,30}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
        Pattern contactPattern = Pattern.compile("^((0)[0-9]{9})$|^((\\+947)[0-9]{8})$");

        boolean nameMatcher = namePattern.matcher(txtSupName.getText()).matches();
        boolean addressMatcher = addressPattern.matcher(txtSupAddress.getText()).matches();
        boolean contactMatcher = contactPattern.matcher(txtSupContact.getText()).matches();

        if (nameMatcher){
            txtSupName.setStyle("-fx-border-color: white");
            btnEdit.setDisable(false);
        }else {
            txtSupName.setStyle("-fx-border-color: red");
            btnEdit.setDisable(true);
        }if (addressMatcher){
            txtSupAddress.setStyle("-fx-border-color: white");
            btnEdit.setDisable(false);
        }else {
            txtSupAddress.setStyle("-fx-border-color: red");
            btnEdit.setDisable(true);
        }if (contactMatcher){
            txtSupContact.setStyle("-fx-border-color: white");
            btnEdit.setDisable(false);
        }else {
            txtSupContact.setStyle("-fx-border-color: red");
            btnEdit.setDisable(true);
        }
    }

    public void itemKeyReleasedOnAction(KeyEvent keyEvent) {
        Pattern upPattern = Pattern.compile("^\\d{1,4}$|^\\d{1,4}(\\.)[0-9]{1,2}$");
        Pattern qtyPattern = Pattern.compile("^[1-9]$|^([1-9][0-9]{1,3})$");

        boolean upMatcher = upPattern.matcher(txtItemUnitPrice.getText()).matches();
        boolean qtyMatcher = qtyPattern.matcher(txtItemQty.getText()).matches();

        if (upMatcher){
            txtItemUnitPrice.setStyle("-fx-border-color: white");
            btnAdd.setDisable(false);
        }else {
            txtItemUnitPrice.setStyle("-fx-border-color: red");
            btnAdd.setDisable(true);
        }if (qtyMatcher){
            double total = 0;
            total+= Double.parseDouble(txtItemUnitPrice.getText())*Double.parseDouble(txtItemQty.getText());
            txtTotalPrice.setText(String.valueOf(total));
            txtItemQty.setStyle("-fx-border-color: white");
            btnAdd.setDisable(false);
        }else {
            txtItemQty.setStyle("-fx-border-color: red");
            btnAdd.setDisable(true);
        }
    }

    public void supNewOnAction(ActionEvent actionEvent) {
        cmbSupId.setVisible(false);
        txtSupId.setDisable(false);
        try {
            txtSupId.setText(new SupplierController().getCustomerId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
