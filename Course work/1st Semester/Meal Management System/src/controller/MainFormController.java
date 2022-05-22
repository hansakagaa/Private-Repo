package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class MainFormController {

    public AnchorPane MainContext;
    public Label lblTotalOrder;
    public Label lblTotalCustomer;
    public Label lblTotalMeals;
    public Label lblTodayDate;
    public Label lblToday;
    public JFXButton btnLogOut;
    public AnchorPane MainFormContext;
    public Button btnHome;
    public Button btnCustomerList;
    public Button btnCustomerManage;
    public Button btnStaff;
    public Button btnItemManage;
    public Button btnMealManage;
    public Button btnStoreItem;
    public Button btnPayment;
    public Label lblTodayTime;

    public void initialize() throws SQLException, ClassNotFoundException {

        loadMainForm();

    }

    private void loadMainForm() throws SQLException, ClassNotFoundException {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd");
        lblToday.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime currantTime = LocalTime.now();
            lblTodayTime.setText(
                    currantTime.getHour()+" : "+currantTime.getMinute()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

        lblTotalCustomer.setText(String.valueOf(new CustomerController().getCustomerCount()));
        lblTotalOrder.setText(String.valueOf(new OrderController().getOrderCount()));
        lblTotalMeals.setText(String.valueOf(new MealPackController().getMealCount()));

    }

    private void loadUI(String filename) throws IOException {
        URL resource = getClass().getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        MainContext.getChildren().clear();
        MainContext.getChildren().add(load);
    }

    private void setUI(String fileName) throws IOException {
        Stage window = (Stage) MainFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + fileName + ".fxml"))));
        window.centerOnScreen();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }

    public void homePageOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MainForm");
    }

    public void customerListOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("CustomerViewForm");
    }

    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("CustomerForm");
    }

    public void staffManageOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("StaffManageForm");
    }

    public void itemManageOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("ItemForm");
    }

    public void mealManageOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("MealForm");
    }

    public void storeItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("StoreItemForm");
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("PaymentForm");
    }

    public void addOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUI("OrderForm");
    }
}

