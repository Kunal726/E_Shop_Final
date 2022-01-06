<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['sid']) and isset($_POST['gst'])) {
            
            $db = new DbOperations_Admin();

            if($db -> Shop_GST($_POST['sid'], $_POST['gst'])) {
                $response['error'] = false;
                $response['message'] = "GST Set Successfully";
                
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