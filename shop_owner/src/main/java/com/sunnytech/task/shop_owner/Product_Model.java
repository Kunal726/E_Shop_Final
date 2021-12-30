package com.sunnytech.task.shop_owner;

public class Product_Model {
    private final String image;
    private final String prdname;
    private final String price;
    private final String qty;
    private final String id;

    public Product_Model(String image, String name, String price, String qty, String id) {
        this.image = image;
        this.prdname = name;
        this.price = price;
        this.qty = qty;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getPrdname() {
        return prdname;
    }

    public String getPrice() {
        return price;
    }

    public String getQty() {
        return qty;
    }

    public String getId() {
        return id;
    }
}
