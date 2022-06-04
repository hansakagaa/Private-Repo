package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SystemReportBO extends SuperBO {

    ArrayList<OrderDTO> getOrderFromDate(String startDate, String endDate) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailsDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;


}
