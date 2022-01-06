<?php
    require_once "DbOperations_Owner.php";
    $response = array();
    $target_dir = "Images/";

   

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if((isset($_POST['name'])) and (isset($_POST['price'])) and (isset($_POST['qty'])) and (isset($_POST['shop'])) and (isset($_POST['owner']))) {
           
            $on = time()." - ".$_POST['shop']." - ".$_POST['name'].".jpg";
            $sn = $_FILES['image']['tmp_name'];
            $dn = "../Images/".$on;
            $db = new DbOperations_Owner();
            
            if($db -> addProduct($_POST['name'], $_POST['qty'], $_POST['price'], $on, $_POST['shop'], $_POST['owner'])) {
                $response['error'] = false;
                $response['message'] = "Product added Successfully";
                move_uploaded_file($sn, $dn);
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