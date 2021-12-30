package com.sunnytech.task.shop_owner;

public class OrderListModel {
    private final String Pname;
    private final String Pqty;
    private final String Pprice;

    public OrderListModel(String pname, String pqty, String pprice) {
        Pname = pname;
        Pqty = pqty;
        Pprice = pprice;
    }

    public String getPname() {
        return Pname;
    }

    public String getPqty() {
        return Pqty;
    }

    public String getPprice() {
        return Pprice;
    }
}
