package controller;

import db.DbConnection;
import model.MealPack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealPackController {

    public boolean saveMealPack(MealPack mealPack) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO `Meal Pack` VALUES(?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,mealPack.getMPId());
        stm.setObject(2,mealPack.getMPDescription());
        stm.setObject(3,mealPack.getMPPackSize());
        stm.setObject(4,mealPack.getMPUnitPrice());

        return stm.executeUpdate()>0;
    }

    public MealPack getMealPack(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Meal Pack` WHERE mPId=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            MealPack mP= new MealPack(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
            return mP;
        } else {
            return null;
        }
    }

    public boolean updateMealPack(MealPack mP) throws SQLException, ClassNotFoundException {
        String query="UPDATE `Meal Pack` SET mPDescription=?, mPPackSize=?, mpUnitPrice=? WHERE mPId=?";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,mP.getMPDescription());
        stm.setObject(2,mP.getMPPackSize());
        stm.setObject(3,mP.getMPUnitPrice());
        stm.setObject(4,mP.getMPId());

        return stm.executeUpdate()>0;
    }

    public List<String> getMealPackIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM `Meal Pack`").executeQuery();
        List<String> mPIds = new ArrayList<>();
        while (rst.next()){
            mPIds.add(
                    rst.getString(1)
            );
        }
        return mPIds;
    }

    public int getMealCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT COUNT(*) FROM `Meal Pack`");
        ResultSet rst = stm.executeQuery();
        int  mealCount =0;
        if (rst.next()) {
            mealCount = rst.getInt(1);
        }
        return mealCount;
    }
}
