package controller;

import db.DbConnection;
import model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController {

    public String getPaymentId() throws SQLException, ClassNotFoundException {
        return getId();
    }

    static String getId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT invoiceNumber FROM `Payment` ORDER BY invoiceNumber DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "IN00-00"+tempId;
            }else if(tempId<99){
                return "IN00-0"+tempId;
            }else{
                return "IN00-"+tempId;
            }

        }else{
            return "IN00-001";
        }
    }

    public boolean savePayment(Payment payment) throws SQLException, ClassNotFoundException {
        String query ="INSERT INTO `Payment` VALUES(?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,payment.getInvoiceNumber());
        stm.setObject(2,payment.getPaymentDate());
        stm.setObject(3,payment.getPaymentDetails());
        stm.setObject(4,payment.getPayAmount());
        return stm.executeUpdate()>0;
    }


}
