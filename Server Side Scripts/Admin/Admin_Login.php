<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['uname']) and isset($_POST['pass'])) {
            
            $db = new DbOperations_Admin();

            $oid = $db -> Login($_POST['uname'], $_POST['pass']);
            if($oid != 0) {
                $response['error'] = false;
                $response['id'] = $oid;
                $response['message'] = "Login Successfull";
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