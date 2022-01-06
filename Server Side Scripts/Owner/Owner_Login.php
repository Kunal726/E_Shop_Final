<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['uname']) and isset($_POST['pass'])) {
            
            $db = new DbOperations_Owner();
            $oid = $db -> owner_login($_POST['uname'], $_POST['pass']);
            if($oid != 0) {
                $owner = $db -> getOwnerbyId($oid);
                $shop = $db -> getShopNamebyID($oid);
                $response['error'] = false;
                $response['id'] = $owner['owner_id'];
                $response['email'] = $owner['owner_username'];
                $response['name'] = $owner['owner_name'];
                $response['shop'] = $shop['shop_name'];
                $response['date'] = $owner['date_created'];
                $response['status'] = $owner['status'];
                $response['category'] = $shop['shop_category'];
                $response['service'] = $shop['delivery_service'];
                $response['monthly_charges'] = $shop['monthly_charges'];
            } else {
                $response['error'] = true;
                $response['message'] = "Invalid Username or Password";
            }

        } else {
            
            $response['error'] = true;
            $response['message'] = "Missing Required Fields";
        }

    } else {
        $response['error'] = true;
        $response['message'] = "Invalid Request";
    }

    echo json_encode($response);
?>