<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['shopName']) and isset($_POST['owner'])) {
            
            $db = new DbOperations_Owner();
            $shopid = $db -> getShopID($_POST['owner'], $_POST['shopName']);
            $payment = $db -> getPayment($shopid);
            if($payment -> num_rows > 0) {
                while($row = $payment -> fetch_assoc())
                {
                    $temp = array();
                    $temp['pid'] = $row['payment_id'];
                    $temp['oid'] = $row['order_id'];
                    $temp['pmode'] = $row['payment_mode'];
                    $temp['pstatus'] = $row['payment_status'];
                    $temp['pamt'] = $row['payment_amount'];

                    array_push($response, $temp);
                }
            } else {
              echo "No data";
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