package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Payment;

import java.sql.SQLException;

public class PaymentFormController {
    public JFXTextField txtInvoiceNo;
    public JFXTextField txtStatus;
    public JFXTextField txtAmount;
    public JFXDatePicker dtpDate;

    public void initialize(){
        setIds();

    }

    private void setIds() {
        try {
            txtInvoiceNo.setText(new PaymentController().getPaymentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void savePaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(
                txtInvoiceNo.getText(),
                dtpDate.getValue(),
                txtStatus.getText(),
                Double.parseDouble(txtAmount.getText())
        );
        if (new PaymentController().savePayment(payment)){
            new Alert(Alert.AlertType.CONFIRMATION, "Save..", ButtonType.CLOSE).show();
            setIds();txtStatus.clear();txtAmount.clear();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..", ButtonType.CLOSE).show();
        }
    }
}
