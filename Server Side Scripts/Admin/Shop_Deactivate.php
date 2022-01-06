<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['oid'])) {
            
            $db = new DbOperations_Admin();

            if($db -> Shop_Deactivate($_POST['oid'])) {
                $response['error'] = false;
                $response['message'] = "Deactivated";
                
            } else {
                $response['error'] = true;
                $response['message'] = "No Data Found";
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