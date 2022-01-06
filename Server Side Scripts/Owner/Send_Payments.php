<?php
    require_once "DbOperations_Owner.php";
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if((isset($_POST['shop'])) and (isset($_POST['owner'])) and (isset($_POST['charges']))) {
           
            $on = time()." - ".$_POST['shop'].".jpg";
            $sn = $_FILES['image']['tmp_name'];
            $dn = "Payments/".$on;
            
            $db = new DbOperations_Owner();

            $shopid = $db -> getShopID( $_POST['owner'], $_POST['shop']);

            if($db -> get_Charges($shopid))
            {
                $response['error'] = false;
                $response['message'] = "You have Already paid for this month";
            } else {
                 
                if($db -> sendToAdmin($shopid, $on, $_POST['charges'])) {
                    move_uploaded_file($sn, $dn);
                    $response['error'] = false;
                    $response['message'] = "Sent to Admin Successfully";
                } else {
                    $response['error'] = true;
                    $response['message'] = "Error";
                }
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