<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['owner'])) {
            
            $db = new DbOperations_Owner();
            
                $owner = $db -> getOwnerbyId($_POST['owner']);
                $shop = $db -> getShopNamebyID($_POST['owner']);

                $response['error'] = false;
                $response['id'] = $owner['owner_id'];
                $response['email'] = $owner['owner_username'];
                $response['name'] = $owner['owner_name'];
                $response['shop'] = $shop['shop_name'];
                $response['date'] = $owner['date_created'];
                $response['category'] = $shop['shop_category'];
                $response['service'] = $shop['delivery_service'];
                $response['status'] = $owner['status'];
                $response['monthly_charges'] = $shop['monthly_charges'];

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