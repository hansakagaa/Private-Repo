package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;

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
    public ComboBox<String> cmdCustomerId;
    public ComboBox<String> cmdItemCode;
    public JFXTextField txtOrderQty;
    public TableView<CartTM> tblList;
    public Label lblDate;
    public JFXTextField txtTotal;
    public JFXTextField txtDiscount;
    public JFXTextField txtCash;
    public JFXTextField txtBalance;
    public JFXButton btnCancel;
    public JFXButton btnSave;


    @FXML
    public void navigateToHome(MouseEvent event) throws IOException {
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/cashier-form.fxml"))));
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    @FXML
    public void navigateToNewCustomer(MouseEvent event) {

    }

    @FXML
    public void navigateToAddCart(MouseEvent event) {

    }

    @FXML
    public void cancelOrderOnAction(ActionEvent actionEvent) {

    }

    @FXML
    public void saveOrderOnAction(ActionEvent actionEvent) {

    }

    @FXML
    public void orderQtyKeyReleased(KeyEvent keyEvent) {

    }

    @FXML
    public void cashBalanceKeyReleased(KeyEvent keyEvent) {

    }

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
