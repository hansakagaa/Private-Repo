package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Staff;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class StaffManageFormController {
    public JFXTextField txtStaffId;
    public JFXTextField txtStaffName;
    public JFXTextField txtStaffAddress;
    public JFXTextField txtStaffNIC;
    public JFXTextField txtStaffContact;
    public JFXTextField txtStaffPosition;
    public JFXButton btnAddNew;
    public JFXButton btnUpdate;
    public AnchorPane staffParent;
    public Button btnNew;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(STF00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-Z. ]+[A-z ]{3,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}$|^[0-9]{9}(V|v)$");
    Pattern contactPattern = Pattern.compile("^((0)[0-9]{9})$|^((\\+947)[0-9]{8})$");
    Pattern positionPattern = Pattern.compile("^[A-z]+$");

    public void initialize() {
        btnAddNew.setDisable(true);
        btnUpdate.setDisable(true);
        storeValidations();

    }

    private void storeValidations() {
        map.put(txtStaffName, namePattern);
        map.put(txtStaffAddress, addressPattern);
        map.put(txtStaffNIC, nicPattern);
        map.put(txtStaffContact, contactPattern);
        map.put(txtStaffPosition, positionPattern);
    }

    public void staffAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Staff staff= new Staff(
                txtStaffId.getText(),
                txtStaffName.getText(),
                txtStaffAddress.getText(),
                txtStaffNIC.getText(),
                txtStaffContact.getText(),
                txtStaffPosition.getText()
        );
        if(new StaffManageController().saveStaff(staff)){
            staffParent.setStyle("-fx-border-color: cyan");
        }
    }

    public void staffUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Staff staff= new Staff(
                txtStaffId.getText(),
                txtStaffName.getText(),
                txtStaffAddress.getText(),
                txtStaffNIC.getText(),
                txtStaffContact.getText(),
                txtStaffPosition.getText()
        );
        if(new StaffManageController().updateStaff(staff)){
            staffParent.setStyle("-fx-border-color: cyan");
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Set Data").show();
        }
    }

    public void staffIdSearch() throws SQLException, ClassNotFoundException {
        Staff staff = new StaffManageController().searchId(txtStaffId.getText());
        if (staff!=null){
            setData(staff);
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }
    }

    private void setData(Staff s) {
        txtStaffName.setText(s.getStName());
        txtStaffAddress.setText(s.getStAddress());
        txtStaffNIC.setText(s.getStNIC());
        txtStaffContact.setText(s.getStContact());
        txtStaffPosition.setText(s.getStPosition());
    }

    public void staffKeyReleased(KeyEvent keyEvent)  {
        Object response = ValidationUtil.validate(map,btnAddNew,btnUpdate);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField jfxTextField = (JFXTextField) response;
                jfxTextField.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }

    public void staffIdNewOnAction(ActionEvent actionEvent) {
        btnUpdate.setDisable(true);
        btnAddNew.setDisable(false);
        txtStaffId.setEditable(false);

        try {
            txtStaffId.setText(new StaffManageController().getStaffId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        txtStaffName.clear();txtStaffAddress.clear();txtStaffContact.clear();
        txtStaffNIC.clear();txtStaffPosition.clear();
    }

    public void KeyPressed(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode() == KeyCode.ENTER){
            boolean matches = idPattern.matcher(txtStaffId.getText()).matches();
            if (matches){
                txtStaffId.setStyle("-fx-border-color:aqua");
                btnUpdate.setDisable(false);
                staffIdSearch();
            }else {
                txtStaffId.setStyle("-fx-border-color: red");
                btnAddNew.setDisable(true);
                btnUpdate.setDisable(true);
            }
        }
    }
}
