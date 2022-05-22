package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.MealPack;

import java.sql.SQLException;

public class MealFormController {
    public JFXTextField txtMealPackId;
    public JFXTextField txtMPDescription;
    public JFXCheckBox cbxNormal;
    public JFXCheckBox cbxMedium;
    public JFXCheckBox cbxLarge;
    public JFXTextField txtUnitPrice;
    public Button btnSearch;
    public JFXButton btnAddNew;
    public JFXButton btnUpdate;
    public AnchorPane mealPackParent;


    public void mPIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        MealPack mealPack = new MealPackController().getMealPack(txtMealPackId.getText());
        if (mealPack!=null){
            setData(mealPack);
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }
    }

    private void setData(MealPack mP) {
        txtMPDescription.setText(mP.getMPDescription());
        txtUnitPrice.setText(String.valueOf(mP.getMPUnitPrice()));
//        set pack size
    }

    public void mealAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        MealPack mealPack= new MealPack(
                txtMealPackId.getText(),
                txtMPDescription.getText(),
                packSize(),
                Double.parseDouble(txtUnitPrice.getText())
        );

        if(new MealPackController().saveMealPack(mealPack)){
            mealPackParent.setStyle("-fx-border-color: cyan");
        }else {
            mealPackParent.setStyle("-fx-border-color: red");
        }
    }

    private String packSize() {
        String packSize = cbxNormal.getText();
        if (cbxMedium.isSelected()){
            packSize=cbxMedium.getText();
        }else if(cbxLarge.isSelected()){
            packSize=cbxLarge.getText();
        }
        return packSize;
    }

    public void mealUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        MealPack mealPack= new MealPack(
                txtMealPackId.getText(),
                txtMPDescription.getText(),
                packSize(),
                Double.parseDouble(txtUnitPrice.getText())
        );
        if(new MealPackController().updateMealPack(mealPack)){
            mealPackParent.setStyle("-fx-border-color: cyan");
        }
    }

    public void mealKeyReleasedOnAction(KeyEvent keyEvent) {

    }
}
