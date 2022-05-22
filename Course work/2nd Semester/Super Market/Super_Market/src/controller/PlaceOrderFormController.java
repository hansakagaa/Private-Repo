package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.tm.CartTM;

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


    public void navigateToHome(MouseEvent event) {

    }

    public void navigateToNewCustomer(MouseEvent event) {

    }

    public void navigateToAddCart(MouseEvent event) {

    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {

    }

    public void saveOrderOnAction(ActionEvent actionEvent) {

    }

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

    public void orderQtyKeyReleased(KeyEvent keyEvent) {

    }
}
