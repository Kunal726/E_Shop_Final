package com.sunnytech.task.eshop_admin;

public class Charges_Model {
    private final String img;
    private final String date;
    private final String amt;

    public Charges_Model(String img, String date, String amt) {
        this.img = Constants.IMAGE_ROOT_URL + img;
        this.date = date;
        this.amt = amt;
    }

    public String getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public String getAmt() {
        return amt;
    }
}
