<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['payid']) and isset($_POST['paystatus'])) {
            
            $db = new DbOperations_Owner();
            
            if($db -> updatePayStatus($_POST['payid'],$_POST['paystatus'])) {

                $response['error'] = false;
                $response['message'] = "Paid Succesfully";
               
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