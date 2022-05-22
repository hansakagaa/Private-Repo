package controller;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT OrderID FROM `Orders` ORDER BY orderId DESC LIMIT 1").executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "O-00"+tempId;
            }else if(tempId<99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

//    public boolean placeOrder(Order order) {
//        Connection con=null;
//        try {
//            con= DBConnection.getInstance().getConnection();
//            con.setAutoCommit(false);
//            PreparedStatement stm =con.prepareStatement(
//                    "INSERT INTO `Orders` VALUES(?,?,?)"
//            );
//            stm.setObject(1, order.getOrderID());
//            stm.setObject(2, order.getOrderDate());
//            stm.setObject(3, order.getCstID());
//
//            if (stm.executeUpdate() > 0){
//                if (saveOrderDetail(order.getOrderID(), order.getItems())){
//                    con.commit();
//                    return true;
//                }else{
//                    con.rollback();
//                    return false;
//                }
//            }else{
//                con.rollback();
//                return false;
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//
//                assert con != null;
//                con.setAutoCommit(true);
//
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//
//        return false;
//    }
//
//    private boolean saveOrderDetail(String orderId, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
//        for (ItemDetails temp : items
//        ) {
//            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO `Order Detail` VALUES(?,?,?,?)"
//            );
//            stm.setObject(1, orderId);
//            stm.setObject(2, temp.getItemCode());
//            stm.setObject(3, temp.getQtyForSell());
//            stm.setObject(4, temp.getDiscount());
//            if (stm.executeUpdate() > 0) {
//
//                if (updateQty(temp.getItemCode(), temp.getQtyForSell())){
//
//                }else{
//                    return false;
//                }
//
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                        "UPDATE `Item` SET QtyOnHand=(QtyOnHand-" + qty + ") WHERE ItemCode='" + itemCode + "'"
//        );
//        return stm.executeUpdate()>0;
//    }

}
