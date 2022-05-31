package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailsDTO;
import view.tm.CartTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemReportFormController {
    public AnchorPane root;
    public JFXDatePicker startDate;
    public JFXDatePicker endDate;
    public JFXTextField txtIncome;
    public JFXTextField txtOrders;
    public Label lblBanner;
    public TableView<CartTM> tblItem;
    public ImageView imgMostItem;
    public ImageView imgLeastItem;

    public void initialize(){
//
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblItem.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
//
    }
//
    @FXML
    public void navigateToHome(MouseEvent event) throws IOException {
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/administrator-form.fxml"))));
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
//
    @FXML
    public void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            switch (icon.getId()) {
                case "imgMostItem":
                    setMostMovableItem();
                    break;
                case "imgLeastItem":
                    setLeastMovableItem();
                    break;
            }
        }
    }

    private void setLeastMovableItem() {

    }

    private void setMostMovableItem() {

    }

//
    @FXML
    public void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgMostItem":
                    lblBanner.setText("Click here view Most movable Item");
                    break;

                case "imgLeastItem":
                    lblBanner.setText("Click here view Least movable Item");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.WHITE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }
//
    @FXML
    public void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);

            lblBanner.setText("Select movable Item");
        }
    }
//
    @FXML
    public void selectDateOnAction(javafx.event.ActionEvent actionEvent) {

        List<OrderDTO> orderDto = new ArrayList<>();
        List<OrderDetailsDTO> orderDetailsDto = new ArrayList<>();
        if (startDate.getValue() != null && endDate.getValue() != null){
            BigDecimal totalInCome = new BigDecimal(0);
            int orderCount = 0;

            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pStm = connection.prepareStatement("SELECT * FROM Orders WHERE orderDate BETWEEN ? AND ?");
                pStm.setString(1,startDate.getValue().toString());
                pStm.setString(2,endDate.getValue().toString());
                ResultSet rst = pStm.executeQuery();
                while (rst.next()){
                    orderDto.add(new OrderDTO(rst.getString("orderID"), rst.getDate("orderDate"), rst.getString("cstID")));
                }
                pStm = connection.prepareStatement("SELECT * FROM `Order Detail` WHERE orderID=?");
                for (OrderDTO dto : orderDto) {
                    pStm.setString(1, dto.getOrderID());
                    rst = pStm.executeQuery();
                    while (rst.next()) {
                        orderDetailsDto.add(new OrderDetailsDTO(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getBigDecimal(4)));
                    }
                }
                orderCount = orderDto.size();

                pStm = connection.prepareStatement("SELECT * FROM Item WHERE itemCode=?");
                for (OrderDetailsDTO dto : orderDetailsDto) {
                    ItemDTO itemDTO = new ItemDTO();
                    pStm.setString(1,dto.getItemCode());
                    rst = pStm.executeQuery();
                    while (rst.next()){
                        itemDTO = new ItemDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getBigDecimal(4), rst.getInt(5));
                    }
                    totalInCome = totalInCome.add(itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(dto.getOrderQty())));
                }

            } catch (SQLIntegrityConstraintViolationException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR," "+ e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR,""+ e.getMessage()).show();
            }

            txtIncome.setText(totalInCome.toString());
            txtOrders.setText(orderCount+"");
        }
    }
}
