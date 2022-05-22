import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFormController {

    public TextField txtUserName;
    public PasswordField pwdPassword;

    public void loginOnAction(ActionEvent actionEvent) {
        if (txtUserName.getText().equals("admin") && pwdPassword.getText().equals("1234")){
            new Alert(Alert.AlertType.CONFIRMATION,"Success !").show();
        }else {

        }
    }
}
