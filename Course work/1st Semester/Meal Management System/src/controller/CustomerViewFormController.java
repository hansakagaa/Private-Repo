package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import view.tm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerViewFormController {
    public JFXTextField txtSearch;
    public Button btnSearch;
    public TableView<CustomerTm> tblCustomer;

    ObservableList<CustomerTm> customerTms = FXCollections.observableArrayList();

    public void initialize() {

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cusContact"));

        try {
            setCustomersToTable(new CustomerController().getAllCustomer());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomersToTable(ArrayList<Customer> customers){

        customers.forEach(e->{
            customerTms.add(
                    new CustomerTm(
                            e.getCusId(),
                            e.getCusName(),
                            e.getCusAddress(),
                            e.getCusContact()
                    )
            );
        });
        tblCustomer.setItems(customerTms);
    }

    public void customerIdSearchOnAction(ActionEvent actionEvent) {


    }
}
