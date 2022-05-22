package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UserFormController {
    public ComboBox<String> cmdStaffId;
    public JFXTextField txtUserName;
    public AnchorPane UserParent;
    public Button btnSave;
    public JFXTextField txtHint;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConform;
    public AnchorPane UserContext;

    public void initialize(){

        btnSave.setDisable(true);

        try {
            setIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setIds() throws SQLException, ClassNotFoundException {
        List<String> staffId = new StaffManageController().setStaffId();
        cmdStaffId.getItems().addAll(staffId);
    }

    public void savePasswordOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        User user = new User(
                txtUserName.getText(),
                cmdStaffId.getValue(),
                txtConform.getText(),
                txtHint.getText()
        );
        if(new UserController().saveUser(user)){
            UserParent.setStyle("-fx-border-color: cyan");
            Stage window = (Stage) UserParent.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
            window.centerOnScreen();
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Try Again.!!").show();
        }
    }

    public void KeyReleasedOnAction(KeyEvent keyEvent) {
        Pattern userNamePattern = Pattern.compile("^[a-z]{5,10}$");
        Pattern passwordPattern = Pattern.compile("^[a-z]{4,10}(@)[0-9]{3,5}$");
        Pattern passwordHintPattern = Pattern.compile("^[A-z]{4,}\\s[A-z]{4,}$");

        boolean uNMatcher = userNamePattern.matcher(txtUserName.getText()).matches();
        boolean pMatcher = passwordPattern.matcher(txtPassword.getText()).matches();
        boolean cMatcher = passwordPattern.matcher(txtConform.getText()).matches();
        boolean pHMatcher = passwordHintPattern.matcher(txtHint.getText()).matches();

        if (uNMatcher){
            txtUserName.setStyle("-fx-border-color: aqua");
        }else {
            btnSave.setDisable(true);
            txtUserName.setStyle("-fx-border-color: red");
        }if (pMatcher){
            txtPassword.setStyle("-fx-border-color: aqua");
        }else {
            btnSave.setDisable(true);
            txtPassword.setStyle("-fx-border-color: red");
        }if (pHMatcher){
            txtHint.setStyle("-fx-border-color: aqua");
        }else {
            btnSave.setDisable(true);
            txtHint.setStyle("-fx-border-color: red");
        }if (cMatcher){
            txtConform.setStyle("-fx-border-color: aqua");
        }else {
            btnSave.setDisable(true);
            txtConform.setStyle("-fx-border-color: red");
        }if (uNMatcher&&pHMatcher&&pMatcher){
            btnSave.setDisable(false);
        }if (!txtConform.getText().equals(txtPassword.getText())){
            txtPassword.setStyle("-fx-border-color: red");
            txtConform.setStyle("-fx-border-color: red");
            btnSave.setDisable(true);
        }
    }
}