package controller;

import db.DBConnection;
import javafx.scene.control.Alert;
import model.CustomerDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Customer`");
        ResultSet rst = stm.executeQuery();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        while (rst.next()) {
            customerDTOS.add(new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return customerDTOS;
    }

    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Customer`").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

//    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                "INSERT INTO `Customer` VALUES(?,?,?,?,?,?,?)");
//        stm.setObject(1, c.getCstID());
//        stm.setObject(2, c.getCustTitle());
//        stm.setObject(3, c.getCustName());
//        stm.setObject(4, c.getCustAddress());
//        stm.setObject(5, c.getCustCity());
//        stm.setObject(6, c.getCustProvince());
//        stm.setObject(7, c.getCustPostalCode());
//
//        return stm.executeUpdate() > 0;
//    }

    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        if(DBConnection.getInstance().getConnection().prepareStatement(
                "DELETE FROM `Customer` WHERE CustID='"+id+"'").executeUpdate()>0
        ) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted from Database").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

//    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                "UPDATE `Customer` SET CustTitle=?, CustName=?, CustAddress=?, City=?,  Province=?, PostalCode=? WHERE CustID=?");
//        stm.setObject(1, c.getCustTitle());
//        stm.setObject(2, c.getCustName());
//        stm.setObject(3, c.getCustAddress());
//        stm.setObject(4, c.getCustCity());
//        stm.setObject(5, c.getCustProvince());
//        stm.setObject(6, c.getCustPostalCode());
//        stm.setObject(7, c.getCstID());
//
//        return stm.executeUpdate() > 0;
//    }

    public CustomerDTO getCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Customer` WHERE CustID=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        } else {
            return null;
        }
    }

    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT CustID FROM `Customer` ORDER BY CustID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "C-00"+tempId;
            }else if(tempId<99){
                return "C-0"+tempId;
            }else{
                return "C-"+tempId;
            }
        }else{
            return "C-001";
        }
    }

}
