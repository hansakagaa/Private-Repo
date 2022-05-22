package model;

import java.time.LocalDate;

public class Payment {
    private String invoiceNumber;
    private LocalDate paymentDate;
    private String paymentDetails;
    private double payAmount;

    public Payment() {
    }

    public Payment(String invoiceNumber, LocalDate paymentDate, String paymentDetails, double payAmount) {
        this.invoiceNumber = invoiceNumber;
        this.paymentDate = paymentDate;
        this.paymentDetails = paymentDetails;
        this.payAmount = payAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", paymentDate=" + paymentDate +
                ", paymentDetails='" + paymentDetails + '\'' +
                ", payAmount=" + payAmount +
                '}';
    }
}
