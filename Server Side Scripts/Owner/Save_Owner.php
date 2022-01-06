<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['name']) and isset($_POST['cat']) and isset($_POST['add']) and isset($_POST['land']) and isset($_POST['city']) and isset($_POST['fromCity']) and isset($_POST['toCity']) and isset($_POST['delServ'])) {
            
            $db = new DbOperations_Owner();
            if($db -> shopData($_POST['name'], $_POST['cat'], $_POST['add'], $_POST['land'], $_POST['city'], $_POST['fromCity'], $_POST['toCity'], $_POST['delServ'])) {
                $response['error'] = false;
                $response['message'] = "Data Saved Successfuly";
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