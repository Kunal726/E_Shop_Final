package com.sunnytech.task.shop_owner

class Constants {
    companion object {
        private const val ROOT_URL = "http://localhost.com/EShop/Owner/"
        const val IMAGE_ROOT_URL = "http://localhost.com/EShop/Payments/"
        const val url_register = ROOT_URL+"Register_Owner.php"
        const val url_save = ROOT_URL+"Save_Owner.php"
        const val url_login_owner = ROOT_URL+"Owner_Login.php"
        const val url_prod_det = ROOT_URL+"Product_Details.php"
        const val prd_list = ROOT_URL+"Product_List.php"
        const val prd_update = ROOT_URL+"Product_Update.php"
        const val prd_order = ROOT_URL+"Product_Order.php"
        const val srv_order = ROOT_URL+"Service_Order.php"
        const val prd_payment = ROOT_URL+"Product_Payments.php"
        const val send_payment = ROOT_URL+"Send_Payments.php"
        const val url_refresh = ROOT_URL+"Refresh.php"
        const val url_change_pay_status = ROOT_URL+"ChangePaymentStatus.php"
        const val url_change_order_status = ROOT_URL+"Update_Order.php"


    }
}
