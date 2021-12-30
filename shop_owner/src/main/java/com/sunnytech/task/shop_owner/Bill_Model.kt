package com.sunnytech.task.shop_owner

class Bill_Model {
    lateinit var User_Name: String
    lateinit var Prod_Name: String
    var Amount: String
    lateinit var Quantity: String
    lateinit var Date: String

    internal constructor(Name: String, Amt: String) {
        User_Name = Name
        Amount = Amt
    }

    internal constructor(PName: String, Amt: String, qty: String, date: String) {
        Prod_Name = PName
        Amount = Amt
        Quantity = qty
        Date = date
    }
}