package com.sunnytech.task.eshop_admin;

import java.util.ArrayList;

public class OrderModel {
    private final String Id;
    private final String Addr;
    private final String Total;
    private final String Discount;
    private final String Finaltotal;
    private final ArrayList<OrderListModel> orderListModels;
    private final String Status;
    private final String Tax;

    public OrderModel(String id, String addr, String total, String discount, String finaltotal, String status, ArrayList<OrderListModel> orderListModels, String tax) {
        Id = id;
        Addr = addr;
        Total = total;
        Discount = discount;
        Finaltotal = finaltotal;
        this.orderListModels = orderListModels;
        Tax = tax;
        if(status.equals("D")) {
            Status = status.concat("elivered");
        } else {
            Status = status.concat("ending");
        }
    }

    public String getId() {
        return Id;
    }

    public String getAddr() {
        return Addr;
    }

    public String getTotal() {
        return Total;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getFinaltotal() {
        return Finaltotal;
    }

    public ArrayList<OrderListModel> getOrderListModels() {
        return orderListModels;
    }

    public String getStatus() {
        return Status;
    }

    public String getTax() {
        return Tax;
    }
}
