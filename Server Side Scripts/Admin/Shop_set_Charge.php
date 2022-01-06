<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['sid']) and isset($_POST['charge'])) {
            
            $db = new DbOperations_Admin();

            if($db -> Shop_charge($_POST['sid'], $_POST['charge'])) {
                $response['error'] = false;
                $response['message'] = "Charges Set Successfully";
                
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