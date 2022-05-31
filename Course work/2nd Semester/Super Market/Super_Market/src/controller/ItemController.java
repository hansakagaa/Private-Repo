package controller;

public class ItemController {
//    public ArrayList<Item> getAllItems() throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                "SELECT * FROM `Item`");
//        ResultSet rst = stm.executeQuery();
//        ArrayList<Item> items = new ArrayList<>();
//        while (rst.next()) {
//            items.add(new Item(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(4),
//                    rst.getInt(5)
//            ));
//        }
//        return items;
//    }
//
//    public boolean saveItem(Item item) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                "INSERT INTO `Item` VALUES(?,?,?,?,?)");
//        stm.setObject(1,item.getItemCode());
//        stm.setObject(2,item.getDescription());
//        stm.setObject(3,item.getPackSize());
//        stm.setObject(4,item.getUnitPrice());
//        stm.setObject(5,item.getQtyOnHand());
//
//        return stm.executeUpdate()>0;
//    }
//
//    public void deleteItem(String code) throws SQLException, ClassNotFoundException {
//        if(DBConnection.getInstance().getConnection().prepareStatement(
//                "DELETE FROM `Item` WHERE ItemCode='"+code+"'").executeUpdate()>0
//        ) {
//            new Alert(Alert.AlertType.CONFIRMATION, "Deleted from Database").show();
//        }else {
//            new Alert(Alert.AlertType.WARNING, "Try Again").show();
//        }
//    }
//
//    public boolean updateItem(Item item) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                "UPDATE `Item` SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
//        stm.setObject(1,item.getDescription());
//        stm.setObject(2,item.getPackSize());
//        stm.setObject(3,item.getUnitPrice());
//        stm.setObject(4,item.getQtyOnHand());
//        stm.setObject(5,item.getItemCode());
//
//        return stm.executeUpdate()>0;
//    }
//
//    public Item getItem(String code) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
//                "SELECT * FROM `Item` WHERE ItemCode=?");
//        stm.setObject(1, code);
//        ResultSet rst = stm.executeQuery();
//        if (rst.next()) {
//            return new Item(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(4),
//                    rst.getInt(5)
//            );
//        } else {
//            return null;
//        }
//    }
//
//    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
//        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
//                "SELECT * FROM Item").executeQuery();
//        List<String> ids= new ArrayList<>();
//        while (rst.next()){
//            ids.add(
//                    rst.getString(1)
//            );
//        }
//        return ids;
//    }

}
