package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderManageBO extends SuperBO {

    boolean exitsCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean exitsCusId(String id) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetails(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteOrderDetails(String orderId, String code) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getOrderFromCusId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailsDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;

}
