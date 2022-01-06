<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['shopName']) and isset($_POST['owner']) and isset($_POST['id']) and isset($_POST['name']) and isset($_POST['price']) and isset($_POST['qty'])) {
            
            $db = new DbOperations_Owner();
            $shopid = $db -> getShopID($_POST['owner'], $_POST['shopName']);
            
            if($db -> updateProduct($_POST['id'],$_POST['name'],$_POST['price'],$_POST['qty'], $shopid)) {

                $response['error'] = false;
                $response['message'] = "Updated Succesfully";
               
            } else {
                $response['error'] = true;
                $response['message'] = "Error";
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