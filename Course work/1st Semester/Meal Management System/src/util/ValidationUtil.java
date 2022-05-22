package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, JFXButton jfxBtn, JFXButton btn) {
        jfxBtn.setDisable(true);
        btn.setDisable(true);
        for (JFXTextField jfxTextField : map.keySet()) {
            Pattern patternValue = map.get(jfxTextField);
            if (!patternValue.matcher(jfxTextField.getText()).matches()) {
                if (!jfxTextField.getText().isEmpty()) {
                    jfxTextField.setStyle("-fx-border-color: red");
                }
                return jfxTextField;
            }
            jfxTextField.setStyle("-fx-border-color: green");
        }
        jfxBtn.setDisable(false);
        btn.setDisable(false);
        return true;
    }
}
