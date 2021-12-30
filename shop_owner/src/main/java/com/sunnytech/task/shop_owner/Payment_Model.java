package com.sunnytech.task.shop_owner;

public class Payment_Model {
    private final String paymentID;
    private final String orderID;
    private final String paymentMode;
    private final String paymentStatus;
    private final String paymentAmount;

    public Payment_Model(String paymentID, String orderID, String paymentMode, String paymentStatus, String paymentAmount) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.paymentMode = paymentMode;
        this.paymentAmount = paymentAmount;
        if(paymentStatus.equalsIgnoreCase(String.valueOf('c')))
            this.paymentStatus = paymentStatus.toUpperCase() + "ompleted";
        else
            this.paymentStatus = paymentStatus.toUpperCase() + "ending";
    }

    public String getPaymentID() {
        return paymentID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }
}
