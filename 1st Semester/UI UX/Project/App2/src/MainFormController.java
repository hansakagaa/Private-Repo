import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class MainFormController {
    public TextField txt;

    public void clickMeOnAction(ActionEvent actionEvent) {
        System.out.println(txt.getText());
    }
}
