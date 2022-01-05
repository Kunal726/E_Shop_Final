package com.sunnytech.task.eshop_admin;

public class ServiceListModel {
    private final String cause;
    private final String price;

    public ServiceListModel(String cause, String price) {
        this.cause = cause;
        this.price = price;
    }

    public String getCause() {
        return cause;
    }

    public String getPrice() {
        return price;
    }
}
