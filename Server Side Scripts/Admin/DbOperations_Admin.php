<?php
    
    class DbOperations_Admin {

        var $con;
        function __construct() {
            include 'DBConnect.php';
            $db = new DBConnect();
            $this -> con = $db -> connect();
            
        }

        function Login($uname, $pass) {
            $stmt = $this -> con -> prepare("SELECT `admin_id` FROM `admin` WHERE `admin_uname` = ? AND `admin_pass` = ?;");
            $stmt -> bind_param("ss", $uname, $pass);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                return $row['admin_id'];
            }
            else
            {
                return 0;
            }
        }

        function getShop() {
            $stmt = $this -> con -> prepare("SELECT `shop_name`, `shop_id`, `shop_category` FROM `shop`;");
            $stmt -> execute();
            return $stmt -> get_result();
        }

        function getDetails($shopid) {
            $stmt = $this -> con -> prepare("SELECT s.`shop_name`, o.owner_name, o.date_created, o.status, s.`tax_gst`, s.`owner_id`, s.`monthly_charges`, s.`shop_category` FROM `shop` s INNER JOIN shop_owner o on s.`owner_id` = o.owner_id WHERE `shop_id` = ? ");
            $stmt -> bind_param("i", $shopid);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                return $row;
            }
            else
            {
                return 0;
            }
        }

        function Shop_Active($oid)
        {
            $stmt = $this -> con -> prepare("UPDATE `shop_owner` SET `status`= 'ACTIVE' WHERE `owner_id` = ?");
            $stmt -> bind_param("i", $oid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }
        
        function Shop_Deactivate($oid)
        {
            $stmt = $this -> con -> prepare("UPDATE `shop_owner` SET `status`= 'INACTIVE' WHERE `owner_id` = ?");
            $stmt -> bind_param("i", $oid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }

        function Shop_GST($sid, $gst)
        {
            $stmt = $this -> con -> prepare("UPDATE `shop` SET `tax_gst`= ? WHERE `shop_id` = ?");
            $stmt -> bind_param("ii", $gst, $sid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }
        
        function Shop_charge($sid, $charge)
        {
            $stmt = $this -> con -> prepare("UPDATE `shop` SET `monthly_charges`= ? WHERE `shop_id` = ?");
            $stmt -> bind_param("ii", $charge, $sid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }

        function get_Charges($sid) {
            $stmt = $this -> con -> prepare("SELECT `date`, `recipt_name`, `amount` FROM `app_charges_payment` WHERE `shop_id` = ? ORDER BY `date` DESC");
            $stmt -> bind_param("i", $sid);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                return $result;
            }
            else
            {
                return null;
            }
        }

        function getBill($shopid) {
            $stmt = $this -> con -> prepare("SELECT b.`order_id`, b.`amount`, b.`discount`, b.`final_amount`, o.order_status, u.user_address, u.user_landmark, u.user_city FROM `bill` b INNER JOIN user_order o ON b.`order_id` = o.order_id INNER JOIN user_address u on b.user_id = u.user_id WHERE b.`shop_id` = ? GROUP BY b.order_id ORDER BY o.order_status DESC");
            $stmt -> bind_param("i", $shopid);
            $stmt -> execute();
            return $stmt -> get_result();
        }

        function getService($shopid) {
            $stmt = $this -> con -> prepare("SELECT b.`order_id`, b.`amount`, o.order_status, o.time , u.user_address, u.user_landmark, u.user_city FROM `bill` b INNER JOIN user_order o ON b.`order_id` = o.order_id INNER JOIN user_address u on b.user_id = u.user_id WHERE b.`shop_id` = ? GROUP BY b.order_id ORDER BY o.order_status DESC");
            $stmt -> bind_param("i", $shopid);
            $stmt -> execute();
            return $stmt -> get_result();
        }

        function getList($order_id)
        {
            $stmt = $this -> con -> prepare("SELECT  p.product_name, o.`product_quantity`, p.product_price FROM `products_ordered` o INNER JOIN product p ON o.`product_id` = p.product_id  WHERE `order_id` = ?");
            $stmt -> bind_param("i", $order_id);
            $stmt -> execute();
            return $stmt -> get_result();
        }

        function getTax($shopid) {
            $stmt = $this -> con -> prepare("SELECT `tax_gst` FROM `bill` WHERE `shop_id` = ?");
            $stmt -> bind_param("i", $shopid);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                return $row['tax_gst'];
            }
            else
            {
                return null;
            }
        }
    }
?>