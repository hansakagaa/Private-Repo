package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail,String> {

    boolean updateOrderDetails(OrderDetail dto) throws SQLException, ClassNotFoundException;

    boolean deleteOrderDetails(String orderId, String code) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
}
