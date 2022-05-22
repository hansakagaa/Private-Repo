package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomerFormController {
    public ComboBox<String> cmbSearch;
    public JFXTextField txtSearch;
    public JFXButton btnSearch;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public AnchorPane customerParent;
    public JFXButton btnEditor;

    Customer customer = new Customer();

    public void initialize(){
        cmbSearch.getItems().add("Customer Id");
        cmbSearch.getItems().add("Customer Contact");

        cmbSearch.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                try {
                    setCustomerData(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        );

    }

    private void setCustomerData(String newValue) throws SQLException, ClassNotFoundException {
        switch (newValue) {
            case "Customer Id":
                customer = new CustomerController().getCustomerForId(txtSearch.getText());
                break;
            case "Customer Contact":
                customer = new CustomerController().getCustomerForContact(txtSearch.getText());
                break;
        }
    }

    public void customerSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (customer!=null){
            setData(customer);
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }
    }

    void setData(Customer c){
        txtCustomerId.setText(c.getCusId());
        txtCustomerName.setText(c.getCusName());
        txtCustomerAddress.setText(c.getCusAddress());
        txtCustomerContact.setText(c.getCusContact());
    }

    public void customerEditOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer= new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtCustomerContact.getText()
        );
        if(new CustomerController().updateCustomer(customer)){
            customerParent.setStyle("-fx-border-color: cyan");
        }else {
            customerParent.setStyle("-fx-border-color: red");
        }

    }

    public void customerKeyPressOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        Pattern searchPattern = Pattern.compile("^((0)[0-9]{9})$|^((\\+947)[0-9]{8})$|^(C00-)[0-9]{3}$|^(C00-)[0-9]{3}(-)[0-9]{3}$");
        boolean matches = searchPattern.matcher(txtSearch.getText()).matches();
        if (matches){
            txtSearch.setStyle("-fx-border-color:aqua");
            btnSearch.setDisable(false);
        }else {
            txtSearch.setStyle("-fx-border-color: red");
            btnSearch.setDisable(true);
        }
    }

    public void contactKeyPressed(KeyEvent keyEvent) {
        Pattern contactPattern = Pattern.compile("^((0)[0-9]{9})$|^((\\+947)[0-9]{8})$");
        boolean matches = contactPattern.matcher(txtCustomerContact.getText()).matches();
        if (matches){
            txtCustomerContact.setStyle("-fx-border-color:aqua");
            btnEditor.setDisable(false);
        }else {
            txtCustomerContact.setStyle("-fx-border-color: red");
            btnEditor.setDisable(true);
        }
    }
}
