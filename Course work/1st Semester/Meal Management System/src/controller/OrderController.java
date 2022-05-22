package controller;

import db.DbConnection;
import model.Order;
import model.OrderDetails;
import model.PrintDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {

    private ResultSet getResultSet(String query) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection
                .getInstance()
                .getConnection()
                .prepareStatement(query)
                .executeQuery();

        return rst;
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        String query = "SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1";
        ResultSet rst = getResultSet(query);
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O00-00"+tempId;
            }else if(tempId<=99){
                return "O00-0"+tempId;
            }else{
                return "O00-"+tempId;
            }
        }else{
            return "O00-001";
        }
    }

    public boolean saveOrderDetails(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO `Order Details` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = getStatement(query);
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "INSERT INTO `Order Details` VALUES(?,?,?,?,?,?,?)");*/
        stm.setObject(1,orderDetails.getOrderId());
        stm.setObject(2,orderDetails.getCusId());
        stm.setObject(3,orderDetails.getOrderQty());
        stm.setObject(4,orderDetails.getOrderTime());
        stm.setObject(5,orderDetails.getOrderDate());
        stm.setObject(6,orderDetails.getTotalAmount());

        return stm.executeUpdate()>0;
    }

    private PreparedStatement getStatement(String query) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection
                .getInstance()
                .getConnection()
                .prepareStatement(query);

        return stm;
    }

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {

        String query = "INSERT INTO `Order` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = getStatement(query);
        /* PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "INSERT INTO `Order` VALUES(?,?,?,?,?,?)");*/
        stm.setObject(1,order.getOrderId());
        stm.setObject(2,order.getMPIds());
        stm.setObject(3,order.getMealQty());
        stm.setObject(4,order.getPaidAmount());
        stm.setObject(5,order.getDiscountPrice());
        stm.setObject(6,order.getTotalAmount());

        return stm.executeUpdate()>0;
    }

    public int getOrderCount() throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM `Order`";
        PreparedStatement stm = getStatement(query);
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT COUNT(*) FROM `Order`");*/
        ResultSet rst = stm.executeQuery();
        int  orderCount =0;
        if (rst.next()) {
            orderCount = rst.getInt(1);
        }
        return orderCount;
    }

    public String getNewBillNo() throws SQLException, ClassNotFoundException {

        String query = "SELECT billNo FROM `Print Details` ORDER BY billNo DESC LIMIT 1";
        ResultSet rst = getResultSet(query);

        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1));
            tempId=tempId+1;
            if (tempId<=9){
                return "00000"+tempId;
            }else if(tempId<=99){
                return "0000"+tempId;
            }else if(tempId<=999){
                return "000"+tempId;
            }else if(tempId<=9999){
                return "00"+tempId;
            }else if(tempId<=99999){
                return "0"+tempId;
            }else {
                return String.valueOf(tempId);
            }
        }else{
            return "000001";
        }

    }

    public boolean savePrintDetails(PrintDetails printDetails) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO `Print Details` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = getStatement(query);

        stm.setObject(1,printDetails.getBillNo());
        stm.setObject(2,printDetails.getCusId());
        stm.setObject(3,printDetails.getDate());
        stm.setObject(4,printDetails.getDiscount());
        stm.setObject(5,printDetails.getQty());
        stm.setObject(6,printDetails.getTotalAmount());

        return stm.executeUpdate()>0;
    }

/*    public String getBillNo() throws SQLException, ClassNotFoundException {
        String query = "SELECT billNo FROM `Print Details` ORDER BY billNo DESC LIMIT 1";
        ResultSet rst = getResultSet(query);

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }*/
}
