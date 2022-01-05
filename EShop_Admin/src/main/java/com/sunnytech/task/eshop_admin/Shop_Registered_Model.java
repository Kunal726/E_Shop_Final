package com.sunnytech.task.eshop_admin;

public class Shop_Registered_Model {
    private final String Shop_Name;
    private final String Shop_Id;
    private final String Shop_cat;

    public String getShop_cat() {
        return Shop_cat;
    }

    public Shop_Registered_Model(String shop_Name, String shop_Id, String shop_cat) {
        Shop_Name = shop_Name;
        Shop_Id = shop_Id;
        Shop_cat = shop_cat;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public String getShop_Id() {
        return Shop_Id;
    }
}
