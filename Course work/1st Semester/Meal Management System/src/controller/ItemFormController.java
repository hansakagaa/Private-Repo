package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.StoreItem;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ItemFormController {
    public ComboBox<String> cmdItem;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemUnitPrice;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public Button btnNew;
    public JFXTextField txtItemTotalPrice;
    public JFXTextField txtItemTotalQty;
    public AnchorPane ItemParent;


    public void initialize(){

        btnEdit.setDisable(true);
        btnAdd.setDisable(true);
        txtItemCode.setDisable(true);

        try {
            setIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmdItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
            txtItemTotalQty.setText(String.valueOf(storeItem.getTotalQty()));
            txtItemTotalPrice.setText(String.valueOf(storeItem.getTotalPrice()));
        }
    }

    private void setIds() throws SQLException, ClassNotFoundException {
        List<String> itemCode = new ItemController().getItemCode();
        cmdItem.getItems().addAll(itemCode);
    }

    public void itemEditOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StoreItem storeItem = new StoreItem(
                cmdItem.getValue(),
                txtItemDescription.getText(),
                Double.parseDouble(txtItemUnitPrice.getText()),
                Integer.parseInt(txtItemTotalQty.getText()),
                Double.parseDouble(txtItemTotalPrice.getText())
        );
        if (new StoreItemController().updateStoreItem(storeItem)){
            txtItemDescription.setStyle("-fx-border-color: aqua");
            txtItemUnitPrice.setStyle("-fx-border-color: aqua");
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Try Again..!!").show();
        }
    }

    public void itemAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StoreItem storeItem = new StoreItem(
                txtItemCode.getText(),
                txtItemDescription.getText(),
                Double.parseDouble(txtItemUnitPrice.getText()),
                Integer.parseInt(txtItemTotalQty.getText()),
                Double.parseDouble(txtItemTotalPrice.getText())
        );
        if (new StoreItemController().saveStoreItem(storeItem)){
            ItemParent.setStyle("-fx-border-color: aqua");
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Try Again..!!").show();
        }
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        btnAdd.setDisable(false);
        btnEdit.setDisable(true);
        cmdItem.setVisible(false);
        txtItemCode.setDisable(false);

        txtItemDescription.clear();txtItemUnitPrice.clear();txtItemTotalQty.clear();txtItemTotalPrice.clear();
        txtItemTotalQty.setText("0");txtItemTotalPrice.setText("0.00");

        try {
            txtItemCode.setText(new ItemController().setItemCode());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void KeyReleasedOnAction(KeyEvent keyEvent) {
        Pattern upPattern = Pattern.compile("^\\d{1,4}$|^\\d{1,4}(\\.)[0-9]{1,2}$");

        boolean upMatcher = upPattern.matcher(txtItemUnitPrice.getText()).matches();

        if (upMatcher){
            txtItemUnitPrice.setStyle("-fx-border-color: white");
            btnEdit.setDisable(false);
            btnAdd.setDisable(false);
            txtItemTotalPrice.setText(String.valueOf(Double.parseDouble(txtItemUnitPrice.getText())*Double.parseDouble(txtItemTotalQty.getText())));
        }else {
            txtItemUnitPrice.setStyle("-fx-border-color: red");
            btnEdit.setDisable(true);
            btnAdd.setDisable(true);
        }
    }
}
