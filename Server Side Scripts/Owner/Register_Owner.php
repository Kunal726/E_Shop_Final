<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['user']) and isset($_POST['pass']) and isset($_POST['name'])) {
            
            $db = new DbOperations_Owner();
            if($db -> registerOwner($_POST['user'], $_POST['pass'], $_POST['name'])) {
                $response['error'] = false;
                $response['message'] = "User Registered Successfuly";
            } else {
                $response['error'] = true;
                $response['message'] = "Some error Occured Please Try Again";
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