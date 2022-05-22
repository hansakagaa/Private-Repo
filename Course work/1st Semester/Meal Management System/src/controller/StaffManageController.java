package controller;

import db.DbConnection;
import model.Staff;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffManageController {

    public String getStaffId() throws SQLException, ClassNotFoundException {
        return getId();
    }
    static String getId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT stId FROM `Staff` ORDER BY stId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "STF00-00"+tempId;
            }else if(tempId<99){
                return "STF00-0"+tempId;
            }else{
                return "STF00-"+tempId;
            }

        }else{
            return "STF00-001";
        }
    }

    public boolean saveStaff(Staff staff) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `Staff` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,staff.getStId());
        stm.setObject(2,staff.getStName());
        stm.setObject(3,staff.getStAddress());
        stm.setObject(4,staff.getStNIC());
        stm.setObject(5,staff.getStContact());
        stm.setObject(6,staff.getStPosition());

        return stm.executeUpdate()>0;
    }

    public boolean updateStaff(Staff staff) throws SQLException, ClassNotFoundException {
        String query="UPDATE `Staff` SET stName=?, stAddress=?, stNIC=?, stContact=?, stPosition=? WHERE stId=?";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,staff.getStName());
        stm.setObject(2,staff.getStAddress());
        stm.setObject(3,staff.getStNIC());
        stm.setObject(4,staff.getStContact());
        stm.setObject(5,staff.getStPosition());
        stm.setObject(6,staff.getStId());

        return stm.executeUpdate()>0;
    }

    public Staff searchId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Staff` WHERE stId=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            Staff s= new Staff(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            return s;
        } else {
            return null;
        }
    }

    public List<String> setStaffId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Staff`").executeQuery();
        List<String> sId = new ArrayList<>();
        while (rst.next()){
            sId.add(
                    rst.getString(1)
            );
        }
        return sId;
    }
}
