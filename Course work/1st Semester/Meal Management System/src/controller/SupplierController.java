package controller;

import db.DbConnection;
import model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController {

    public List<String> getSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Supplier`").executeQuery();
        List<String> supIds = new ArrayList<>();
        while (rst.next()){
            supIds.add(
                    rst.getString(1)
            );
        }
        return supIds;
    }

    public Supplier getSupplier(String supIds) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Supplier` WHERE supId=?");
        stm.setObject(1, supIds);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            Supplier supplier= new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            return supplier;
        } else {
            return null;
        }
    }

    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "UPDATE `Supplier` SET supName=?, supContact=?, supAddress=? WHERE supId=?");
        stm.setObject(1,supplier.getSupName());
        stm.setObject(2,supplier.getSupContact());
        stm.setObject(3,supplier.getSupAddress());
        stm.setObject(4,supplier.getSupId());

        return stm.executeUpdate()>0;
    }

    public String getCustomerId() throws SQLException, ClassNotFoundException {
        return getId();
    }
    static String getId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT supId FROM `Supplier` ORDER BY supId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "SP00-00"+tempId;
            }else if(tempId<99){
                return "SP00-0"+tempId;
            }else{
                return "SP00-"+tempId;
            }

        }else{
            return "SP00-001";
        }
    }

    public boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `Supplier` VALUES(?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,supplier.getSupId());
        stm.setObject(2,supplier.getSupName());
        stm.setObject(3,supplier.getSupContact());
        stm.setObject(4,supplier.getSupAddress());

        return stm.executeUpdate()>0;
    }
}
