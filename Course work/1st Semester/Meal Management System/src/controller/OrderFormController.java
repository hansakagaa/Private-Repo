package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.MealTm;
import view.tm.OrderTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class OrderFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContact;
    public JFXComboBox<String> cmbMPId;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtOrderId;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtOrderQty;
    public JFXTextField txtAmount;
    public JFXTextField txtDiscount;
    public JFXTextField txtTotal;
    public TableView<OrderTm> tblOrders;
    public AnchorPane customerParent;
    public JFXTextField txtPackSize;
    public Button btnRemove;
    public JFXButton btnAddOrder;
    public JFXButton btnPrintBill;
    public JFXButton btnSave;
    public AnchorPane orderParent;
    public AnchorPane mealParent;
    public JFXButton btnCustomer;
    public Button btnSearch;
    public JFXTextField txtCash;
    public JFXTextField txtBalance;

    Customer customer = new Customer();
    ObservableList<OrderTm> orderTms = FXCollections.observableArrayList();
    int selectRow = -1;

    public void initialize(){

        btnRemove.setDisable(true);
        btnAddOrder.setDisable(true);
        btnPrintBill.setDisable(true);
        btnSave.setDisable(true);
        btnCustomer.setDisable(true);

        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("mealPackId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("mealPackSize"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("mealPackQty"));
        loadDateAndTime();
        setIds();

        try {
            lordMealPackIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbMPId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setMealPackIds(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblOrders.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->{
            selectRow = (int) newValue;
            btnRemove.setDisable(false);
        } );
    }

    private void lordMealPackIds() throws SQLException, ClassNotFoundException {
        List<String> mealPackIds = new MealPackController().getMealPackIds();
        cmbMPId.getItems().addAll(mealPackIds);
    }

    private void setMealPackIds(String mealPackId) throws SQLException, ClassNotFoundException {
        MealPack mealPack = new MealPackController().getMealPack(mealPackId);
        if (mealPack!=null){
            txtDescription.setText(mealPack.getMPDescription());
            txtUnitPrice.setText(String.valueOf(mealPack.getMPUnitPrice()));
            txtPackSize.setText(mealPack.getMPPackSize());
        }
    }

    private void setIds() {
        try {
            txtCustomerId.setText(new CustomerController().getCustomerId());
            txtOrderId.setText(new OrderController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnRemove.setDisable(true);
        setMealPack();
        calculateAmount();
        setMeal();
        txtUnitPrice.clear();txtDescription.clear();txtPackSize.clear();
    }

    private void calculateAmount() {
        double amount = 0;
        for (OrderTm tm:orderTms) {
            amount+=tm.getTotal();
        }
        txtAmount.setText(String.valueOf(amount));

        double total = amount;
        total-= amount*Double.parseDouble(txtDiscount.getText())/100;
        txtTotal.setText(String.valueOf(total));

    }

    private void setMealPack() {
        OrderTm tm = new OrderTm(
                cmbMPId.getValue(),
                txtPackSize.getText(),
                Integer.parseInt(txtOrderQty.getText()),
                Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(txtOrderQty.getText())
        );
        int rowNum = isExists(tm);
        if (rowNum==-1) {
            orderTms.add(tm);
        }else {
            OrderTm temp = orderTms.get(rowNum);
            OrderTm newTm = new OrderTm(
                    temp.getMealPackId(),
                    temp.getMealPackSize(),
                    temp.getMealPackQty() + Integer.parseInt(txtOrderQty.getText()),
                    temp.getTotal()+Double.parseDouble(txtUnitPrice.getText()) * Integer.parseInt(txtOrderQty.getText())
            );
            orderTms.remove(rowNum);
            orderTms.add(newTm);
        }
        tblOrders.setItems(orderTms);
    }

    private int isExists(OrderTm tm){
        for (int i=0; i<orderTms.size(); i++){
            if (tm.getMealPackId().equals(orderTms.get(i).getMealPackId())){
                return i;
            }
        }
        return -1;
    }

    ArrayList<MealTm> mealData= new ArrayList<>();
    private void setMeal(){
        mealData.add(
                new MealTm(
                        cmbMPId.getValue(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtOrderQty.getText()),
                        Double.parseDouble(txtOrderQty.getText())*Double.parseDouble(txtUnitPrice.getText())
                )
        );

    }

    public void billPrintOnAction(ActionEvent actionEvent) {
        String billNo = null;
        try {
            billNo = new OrderController().getNewBillNo();
            setPrintDetails(billNo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            JasperReport compiledReport = (JasperReport)JRLoader.loadObject(this.getClass().getResource("/view/report/Eats&Treats.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport,hashMap(billNo),new JRBeanCollectionDataSource(mealData));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void setPrintDetails(String billNo) throws SQLException, ClassNotFoundException {
        double discount = Double.parseDouble(txtAmount.getText()) * Double.parseDouble(txtDiscount.getText()) / 100;;
        PrintDetails printDetails = new PrintDetails(
                billNo,
                txtCustomerId.getText(),
                lblDate.getText(),
                discount,
                Integer.parseInt(txtOrderQty.getText()),
                Double.parseDouble(txtTotal.getText())
        );

        if(new OrderController().savePrintDetails(printDetails)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved Print Details.!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Try Again.!!").show();
        }
    }


    private HashMap hashMap(String billNo){

        String cusId = txtCustomerId.getText();
        double total = Double.parseDouble(txtAmount.getText());
        double discount = Double.parseDouble(txtAmount.getText()) * Double.parseDouble(txtDiscount.getText()) / 100;
        double nxtTotal = Double.parseDouble(txtTotal.getText());
        double cash = Double.parseDouble(txtCash.getText());
        double balance = cash-Double.parseDouble(txtTotal.getText());

        HashMap orderData = new HashMap();
        orderData.put("cusId",cusId);
        orderData.put("billNo",billNo);
        orderData.put("total",total);
        orderData.put("discount",discount);
        orderData.put("nxtTotal",nxtTotal);
        orderData.put("cash",cash);
        orderData.put("balance",balance);


        return orderData;
    }

    public void saveOrderOnAction(ActionEvent actionEvent) {
        btnPrintBill.setDisable(false);
        btnAddOrder.setDisable(true);
        btnSave.setDisable(true);

        try {
            setOrderDetails();
            setOrder();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setOrder() throws SQLException, ClassNotFoundException {
        Order order = new Order(
                txtOrderId.getText(),
                String.valueOf(getMIds()),
                String.valueOf(getMQty()),
                Double.parseDouble(txtAmount.getText()),
                Double.parseDouble(txtDiscount.getText()),
                Double.parseDouble(txtTotal.getText())
        );
        if (new OrderController().saveOrder(order)){
            mealParent.setStyle("-fx-border-color: cyan");
        }else {
            mealParent.setStyle("-fx-border-color: red");
        }
    }

    private ArrayList<Integer> getMQty() {
        ArrayList<Integer> mQty = new ArrayList();
        for (int i=0;  i<orderTms.size(); i++) {
            mQty.add(i, (orderTms.get(i).getMealPackQty()));
        }
        return mQty;
    }

    private ArrayList<String> getMIds() {
        ArrayList<String> mIds = new ArrayList();
        for (int i=0;  i<orderTms.size(); i++) {
            mIds.add(i, orderTms.get(i).getMealPackId());
        }
        return mIds;
    }

    private void setOrderDetails() throws SQLException, ClassNotFoundException {
        OrderDetails orderDetails = new OrderDetails(
                txtOrderId.getText(),
                txtCustomerId.getText(),
                orderTms.size(),
                lblTime.getText(),
                lblDate.getText(),
                Double.parseDouble(txtTotal.getText())
        );
        if (new OrderController().saveOrderDetails(orderDetails)){
            orderParent.setStyle("-fx-border-color: cyan");
        }else {
            orderParent.setStyle("-fx-border-color: red");
        }
    }

    public void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime currantTime = LocalTime.now();
            lblTime.setText(
                    currantTime.getHour()+" : "+currantTime.getMinute()+" : "+currantTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void contactSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        customer = new CustomerController().getCustomerForContact(txtCustomerContact.getText());
        if (customer!=null){
            setData(customer);
            txtCustomerContact.setStyle("-fx-border-color:aqua");
        }
    }

    private void setData(Customer c) {
        btnCustomer.setDisable(true);
        txtCustomerId.setText(c.getCusId());
        txtCustomerName.setText(c.getCusName());
        txtCustomerAddress.setText(c.getCusAddress());
    }

    public void KeyReleasedOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        Pattern contactPattern = Pattern.compile("^((0)[0-9]{9})$|^((\\+947)[0-9]{8})$");
        Pattern discountPattern = Pattern.compile("^\\d{1,2}$|^\\d{1,2}(\\.)[0-9]{1,2}$");
        Pattern qtyPattern = Pattern.compile("^[1-9]$|^([1-9][0-9])$");
        Pattern cashPattern = Pattern.compile("^\\d{1,9}$|^\\d{1,9}(\\.)[0-9]{1,2}$");
        boolean contact = contactPattern.matcher(txtCustomerContact.getText()).matches();
        boolean discount = discountPattern.matcher(txtDiscount.getText()).matches();
        boolean qty = qtyPattern.matcher(txtOrderQty.getText()).matches();
        boolean cash = cashPattern.matcher(txtCash.getText()).matches();
        if (contact){
            customer = new CustomerController().getCustomerForContact(txtCustomerContact.getText());
            if (customer!=null){
                btnCustomer.setDisable(false);
                setData(customer);
                customerParent.setStyle("-fx-border-color:aqua");
                txtCustomerContact.setStyle("-fx-border-color:white");
                btnSearch.setStyle("-fx-background-color:#eeeeee");
                txtCustomerId.setStyle("-fx-background-color:#eeeeee");
            }else {
                txtCustomerContact.setStyle("-fx-border-color:aqua");
                btnCustomer.setDisable(false);
            }
        }else {
            txtCustomerContact.setStyle("-fx-border-color: red");
            btnCustomer.setDisable(true);
        }
        if (discount) {
            double total = Double.parseDouble(txtAmount.getText());
            total -= Double.parseDouble(txtAmount.getText()) * Double.parseDouble(txtDiscount.getText()) / 100;
            txtTotal.setText(String.valueOf(total));
            btnSave.setDisable(false);
            txtDiscount.setStyle("-fx-border-color: white");
        }else {
            txtDiscount.setStyle("-fx-border-color: red");
            btnSave.setDisable(true);
        }
        if (qty){
            txtOrderQty.setStyle("-fx-border-color: white");
            btnAddOrder.setDisable(false);
        }else {
            txtOrderQty.setStyle("-fx-border-color: red");
            btnAddOrder.setDisable(true);
            btnSave.setDisable(true);
        }if (cash){
            double tempCash = Double.parseDouble(txtCash.getText());
            double balance = tempCash-Double.parseDouble(txtTotal.getText());
            txtBalance.setText(String.valueOf(balance));
            txtCash.setStyle("-fx-border-color: white");
        }else {
            txtCash.setStyle("-fx-border-color: red");
            btnSave.setDisable(true);
        }
    }

    public void rowRemoveOnAction(ActionEvent actionEvent) {
        if (selectRow!=-1) {
            orderTms.remove(selectRow);
            calculateAmount();
            tblOrders.refresh();
        }else {
            btnSave.setDisable(true);
            new Alert(Alert.AlertType.WARNING,"Please Select a Row").showAndWait();
        }
    }

    public void CustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer c= new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtCustomerContact.getText()
        );
        if(new CustomerController().saveCustomer(c)){
            customerParent.setStyle("-fx-border-color: cyan");
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Try Again.!!").show();
        }
    }
}
