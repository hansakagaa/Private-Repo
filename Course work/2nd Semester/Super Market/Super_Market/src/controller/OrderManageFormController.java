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

public class OrderManageFormController {
    public AnchorPane root;
    public ComboBox<String> cmdCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public TableView<String> tblOrderId;
    public Label lblOrderId;
    public TableView<CartTM> tblOrderDetails;
    public Label lblItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtTotal;
    public JFXTextField txtOrderQty;
    public JFXButton btnRemove;
    public JFXButton btnAdd;
    public JFXTextField txtPresTotal;
    public JFXTextField txtNewTotal;
    public JFXTextField txtCash;
    public JFXTextField txtBalance;
    public JFXButton btnCancel;
    public JFXButton btnSave;

    public void navigateToHome(MouseEvent event) {

    }

    public void itemRemoveOnAction(ActionEvent actionEvent) {

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

    public void itemAddOnAction(ActionEvent actionEvent) {

    }

    public void cancelOrderEditOnAction(ActionEvent actionEvent) {

    }

    public void saveOrderEditOnAction(ActionEvent actionEvent) {

    }

    public void orderSearchKeyReleased(KeyEvent keyEvent) {

    }

    public void orderQtyKeyReleased(KeyEvent keyEvent) {

    }

    public void cashBalanceKeyReleased(KeyEvent keyEvent) {

    }
}
