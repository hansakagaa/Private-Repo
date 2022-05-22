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
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginFormController {

    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public JFXButton btnOwner;
    public AnchorPane LoginContext;

    public void initialize() {
        btnLogin.setDisable(true);
    }

    public void loginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        User user = new UserController().getUsers(txtUserName.getText());
        if (user!=null){
            if (user.getPassword().equals(txtPassword.getText())){
                Stage window = (Stage) LoginContext.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"))));
                window.centerOnScreen();
            }else {
                txtPassword.setStyle("-fx-border-color: red");
            }
        }else {
            txtUserName.setStyle("-fx-border-color: red");
        }
    }

    public void forgetOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) LoginContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserForm.fxml"))));
        window.centerOnScreen();
    }

    public void ownerOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) LoginContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OwnerForm.fxml"))));
        window.centerOnScreen();
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
