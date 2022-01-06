<?php
    
    class DbOperations_Owner {

        var $con;
        function __construct() {
            include 'DBConnect.php';
            $db = new DBConnect();
            $this -> con = $db -> connect();
        }

        function registerOwner($username, $pass, $name)
        {
            $password = md5($pass);
            $stmt = $this -> con -> prepare("INSERT INTO `shop_owner`(`owner_username`, `owner_password`, `owner_name`) VALUES (?, ?, ?);");
            $stmt -> bind_param("sss", $username, $password, $name);
            if($stmt->execute()) {
                return true;
            }
            else {
                return false;
            }
        }

        function shopData($name, $category, $address, $landmark, $city, $fromcity, $tocity, $delServ)
        {
            $own_id = $this -> con -> query("SELECT `owner_id` from shop_owner ORDER BY `owner_id` DESC LIMIT 1");
            $row = $own_id -> fetch_assoc();

            $stmt = $this -> con -> prepare("INSERT INTO `shop`(`owner_id`, `shop_name`, `shop_address`, `shop_landmark`, `shop_city`, `shop_category`, `delivery_service`, `delivery_from`, `delivery_to`) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?);");
            $stmt -> bind_param("issssssss", $row['owner_id'], $name, $address, $landmark, $city, $category, $delServ, $fromcity, $tocity);
            if($stmt->execute()) {
                return true;
            }
            else {
                return false;
            }
        }

        function owner_login($uname, $pass)
        {
            $password = md5($pass);
            $stmt = $this -> con -> prepare("SELECT `owner_id` FROM `shop_owner` WHERE `owner_username` = ? AND `owner_password`= ?;");
            $stmt -> bind_param("ss", $uname, $password);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                return $row['owner_id'];
            }
            else
            {
                return 0;
            }

        }

        function getShopNamebyID($ownid)
        {
            $ownerid = (int) $ownid;
            $stmt = $this -> con -> prepare("SELECT `shop_name`, `shop_category`, `delivery_service`, `monthly_charges` FROM `shop` WHERE `owner_id`= ?;");
            $stmt -> bind_param("i", $ownerid);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                return $row;
            }
            else
            {
                return "";
            }

        }

        function getOwnerbyId($ownerid)
        {
            $stmt = $this -> con -> prepare("SELECT * FROM `shop_owner` WHERE `owner_id`= ?;");
            $stmt -> bind_param("i", $ownerid);
            $stmt -> execute();
            return $stmt -> get_result() -> fetch_assoc();
        }

        function addProduct($prodName, $prodqty, $prodprice, $prodimg, $shop_name, $owner_id)
        {
            $id = $this -> getShopID($owner_id, $shop_name);

            $qty = (int)$prodqty;
            $price = (int)$prodprice;
            $stmt1 = $this -> con -> prepare("INSERT INTO `product`( `product_name`, `product_quantity`, `product_price`, `product_image`, `shop_id`) VALUES (?,?,?,?,?)");
            $stmt1 -> bind_param("siisi", $prodName, $qty, $price, $prodimg, $id);
            if($stmt1->execute()) {
                return true;
            }
            else {
                return false;
            }

        }

        function getShopID($owner_id, $shop_name) {
            $id = 0;
            $ownerid = (int) $owner_id;
            $stmt = $this -> con -> prepare("SELECT `shop_id` FROM shop WHERE `owner_id`= ? and `shop_name`= ? ");
            $stmt -> bind_param("is", $ownerid, $shop_name);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                $id = $row['shop_id'];
            }

            $stmt -> close();
            return $id;
        }

        function getProduct($shopid) {
            $stmt = $this -> con -> prepare("SELECT `product_id`, `product_image`, `product_name`, `product_price`, `product_quantity` FROM `product` WHERE `shop_id` = ?;");
            $stmt -> bind_param("i", $shopid);
            $stmt -> execute();
            return $stmt -> get_result();
        }

        function updateProduct($id, $name, $price, $qty, $shopid) {
            $pid = (int) $id;
            $pprice = (int) $price;
            $pqty = (int) $qty;
            $stmt = $this -> con -> prepare("UPDATE `product` SET`product_name`= ? ,`product_quantity`= ? ,`product_price`= ? WHERE `product_id` = ? AND `shop_id` = ?;");
            $stmt -> bind_param("siiii", $name, $pqty, $pprice, $pid, $shopid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
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

        function getPayment($shopid) {
            $stmt = $this -> con -> prepare("SELECT b.`order_id`, p.payment_mode, p.payment_id, p.payment_status, p.payment_amount  FROM `bill` b INNER JOIN user_payment p on b.`payment_id` = p.payment_id WHERE `shop_id` = ? GROUP BY `payment_id` ORDER BY p.payment_status DESC");
            $stmt -> bind_param("i", $shopid);
            $stmt -> execute();
            return $stmt -> get_result();
        }

        function sendToAdmin($shop_id, $name, $amt)
        {
            $stmt = $this -> con -> prepare("INSERT INTO `app_charges_payment`(`recipt_name`, `shop_id`, `amount`) VALUES (?,?,?)");
            $stmt -> bind_param("sii", $name, $shop_id, $amt);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }


        function updatePayStatus($pid, $pstatus) {
            $stmt = $this -> con -> prepare("UPDATE `user_payment` SET `payment_status`=? WHERE `payment_id` = ?");
            $stmt -> bind_param("si", $pstatus, $pid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }

        function updateOrderStatus($oid, $ostatus) {
            $stmt = $this -> con -> prepare("UPDATE `user_order` SET `order_status`= ? WHERE `order_id` = ?");
            $stmt -> bind_param("si", $ostatus, $oid);
            if($stmt -> execute())
            {
                return true;
            }
            return false;
        }

        function get_Charges($sid) {
            $stmt = $this -> con -> prepare("SELECT `date` FROM `app_charges_payment` WHERE `shop_id` = ? ORDER BY `date` DESC LIMIT 1");
            $stmt -> bind_param("i", $sid);
            $stmt -> execute();
            $result = $stmt -> get_result();
            if($result -> num_rows > 0)
            {
                $row = $result -> fetch_assoc();
                $mon = date( "m" ,strtotime($row['date']));
                $year = date( "Y" ,strtotime($row['date']));
                $cmon = date("m");
                $cyear = date("Y");

                if($mon == $cmon && $year == $cyear) {
                    return true;
                }
            }

            return false;
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