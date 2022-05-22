package controller;

import db.DbConnection;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `User` VALUES(?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,user.getUserId());
        stm.setObject(2,user.getStId());
        stm.setObject(3,user.getPassword());
        stm.setObject(4,user.getPasswordHint());

        return stm.executeUpdate()>0;
    }

    public User getUsers(String useName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `User` WHERE userId=?");
        stm.setObject(1, useName);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            User user= new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            return user;
        } else {
            return null;
        }
    }
}
