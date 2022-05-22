package controller;

import db.DbConnection;
import model.StoreItem;
import model.SupplyDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreItemController {
    public StoreItem getStoreItem(String itemCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Stores Item` WHERE sItemCode=?");
        stm.setObject(1, itemCode);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            StoreItem storeItem= new StoreItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
            return storeItem;
        } else {
            return null;
        }
    }

    public boolean setSupplyDetails(SupplyDetails supplyDetails) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `Supply Details` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,supplyDetails.getSupId());
        stm.setObject(2,supplyDetails.getsItemCode());
        stm.setObject(3,supplyDetails.getSupQty());
        stm.setObject(4,supplyDetails.getsIDescription());
        stm.setObject(5,supplyDetails.getsItemUP());
        stm.setObject(6,supplyDetails.getDate());

        return stm.executeUpdate()>0;
    }

    public boolean updateStoreItem(StoreItem newItem) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "UPDATE `Stores Item` SET sItemDescription=?, sItemUnitPrice=?, totalQty=?, totalPrice=? WHERE sItemCode=?");
        stm.setObject(1,newItem.getsItemDescription());
        stm.setObject(2,newItem.getsItemUnitPrice());
        stm.setObject(3,newItem.getTotalQty());
        stm.setObject(4,newItem.getTotalPrice());
        stm.setObject(5,newItem.getsItemCode());

        return stm.executeUpdate()>0;
    }

    public boolean saveStoreItem(StoreItem storeItem) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `Stores Item` VALUES(?,?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,storeItem.getsItemCode());
        stm.setObject(2,storeItem.getsItemDescription());
        stm.setObject(3,storeItem.getsItemUnitPrice());
        stm.setObject(4,storeItem.getTotalQty());
        stm.setObject(5,storeItem.getTotalPrice());

        return stm.executeUpdate()>0;
    }
}
