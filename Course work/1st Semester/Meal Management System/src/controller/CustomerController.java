package controller;

import db.DbConnection;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {

    public String getCustomerId() throws SQLException, ClassNotFoundException {
        return getId();
    }

    static String getId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT cusId FROM `Customer` ORDER BY cusId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "C00-00"+tempId;
            }else if(tempId<=99){
                return "C00-0" + tempId;
            } else if (tempId<=999){
                return "C00-" + tempId;
            }else if(tempId<=9999){
                return "C00-999-00"+tempId;
            }else if (tempId<=99999){
                return "C00-999-0"+tempId;
            }else{
                return "C00-999-"+tempId;
            }
        }else{
            return "C00-001";
        }
    }

    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `Customer` VALUES(?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,customer.getCusId());
        stm.setObject(2,customer.getCusName());
        stm.setObject(3,customer.getCusAddress());
        stm.setObject(4,customer.getCusContact());

        return stm.executeUpdate()>0;
    }

    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "UPDATE `Customer` SET cusName=?, cusAddress=?, cusContact=? WHERE cusId=?"
        );
        stm.setObject(1,c.getCusName());
        stm.setObject(2,c.getCusAddress());
        stm.setObject(3,c.getCusContact());
        stm.setObject(4,c.getCusId());
        return stm.executeUpdate()>0;
    }

    public Customer getCustomerForId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Customer` WHERE cusId=?");
        return getCustomer(id, stm);
    }

    public Customer getCustomerForContact(String contact) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Customer` WHERE cusContact=?");
        return getCustomer(contact, stm);
    }

    private Customer getCustomer(String customer, PreparedStatement stm) throws SQLException {
        stm.setObject(1, customer);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            Customer c1= new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            return c1;
        } else {
            return null;
        }
    }

    public ArrayList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Customer`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return customers;
    }

    public int getCustomerCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT COUNT(*) FROM `Customer`");
        ResultSet rst = stm.executeQuery();
        int  customerCount =0;
        if (rst.next()) {
            customerCount = rst.getInt(1);
        }
        return customerCount;
    }
}
