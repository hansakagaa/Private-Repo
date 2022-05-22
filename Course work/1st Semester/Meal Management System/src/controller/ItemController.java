package controller;

import db.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    public List<String> getItemCode() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Stores Item`").executeQuery();
        List<String> sItCode = new ArrayList<>();
        while (rst.next()){
            sItCode.add(
                    rst.getString(1)
            );
        }
        return sItCode;
    }

    public String setItemCode() throws SQLException, ClassNotFoundException {
        return getId();
    }
    static String getId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                        "SELECT sItemCode FROM `Stores Item` ORDER BY sItemCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "ST00-00"+tempId;
            }else if(tempId<99){
                return "ST00-0"+tempId;
            }else{
                return "ST00-"+tempId;
            }

        }else{
            return "ST00-001";
        }
    }
}
