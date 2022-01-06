<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['oid']) and isset($_POST['ostatus'])) {
            
            $db = new DbOperations_Owner();
            
            if($db -> updateOrderStatus($_POST['oid'],$_POST['ostatus'])) {

                $response['error'] = false;
                $response['message'] = "Order Completed Succesfully";
               
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

    echo $response['message'];
?>