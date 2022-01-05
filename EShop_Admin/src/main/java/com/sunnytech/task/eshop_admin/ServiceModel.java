package com.sunnytech.task.eshop_admin;

import java.util.ArrayList;

public class ServiceModel {
    private final String OrderId;
    private final String Time;
    private final String Address;
    private final String Status;
    private final String Price;
    private final String Tax;
    private final ArrayList<ServiceListModel> serviceListModels;

    public String getTax() {
        return Tax;
    }

    public ServiceModel(String orderId, String time, String address, String status, ArrayList<ServiceListModel> serviceListModels, String price, String tax) {
        OrderId = orderId;
        Time = time;
        Address = address;
        this.serviceListModels = serviceListModels;
        this.Price = price;
        Tax = tax;
        if(status.equalsIgnoreCase("C")) {
            Status = status.concat("ompleted").toUpperCase();
        } else {
            Status = status.concat("ending").toUpperCase();
        }
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getTime() {
        return Time;
    }

    public String getAddress() {
        return Address;
    }

    public String getStatus() {
        return Status;
    }

    public ArrayList<ServiceListModel> getServiceListModels() {
        return serviceListModels;
    }

    public String getPrice() {
        return Price;
    }
}
