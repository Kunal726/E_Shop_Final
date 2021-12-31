package com.sunnytech.task.shop_owner;

import java.util.ArrayList;

public class ServiceModel {
    private final String OrderId;
    private final String Time;
    private final String Address;
    private final String Status;
    private final String Price;
    private final ArrayList<ServiceListModel> serviceListModels;

    public ServiceModel(String orderId,  String time, String address, String status, ArrayList<ServiceListModel> serviceListModels, String price) {
        OrderId = orderId;
        Time = time;
        Address = address;
        this.serviceListModels = serviceListModels;
        this.Price = price;
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
