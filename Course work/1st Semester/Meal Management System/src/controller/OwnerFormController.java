package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class OwnerFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public AnchorPane OwnerContext;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("owner")&&txtPassword.getText().equals("owner@1234")){
            Stage window = (Stage) OwnerContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"))));
            window.centerOnScreen();
        }else {
            txtUserName.setStyle("-fx-border-color: red");
            txtPassword.setStyle("-fx-border-color: red");
        }
    }

    public void KeyReleasedOnAction(KeyEvent keyEvent) {
        Pattern userNamePattern = Pattern.compile("^[a-z]{5,10}$");
        Pattern passwordPattern = Pattern.compile("^[a-z]{4,10}(@)[0-9]{3,5}$");

        boolean uNMatcher = userNamePattern.matcher(txtUserName.getText()).matches();
        boolean pMatcher = passwordPattern.matcher(txtPassword.getText()).matches();

        if (uNMatcher){
            txtUserName.setStyle("-fx-border-color: aqua");
        }else {
            btnLogin.setDisable(true);
            txtUserName.setStyle("-fx-border-color: red");
        }if (pMatcher){
            txtPassword.setStyle("-fx-border-color: aqua");
        }else {
            btnLogin.setDisable(true);
            txtPassword.setStyle("-fx-border-color: red");
        }if (uNMatcher&&pMatcher){
            btnLogin.setDisable(false);
        }
    }
}
