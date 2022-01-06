<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['shopid'])) {
            
            $db = new DbOperations_Admin();

            $row = $db -> getDetails($_POST['shopid']);
            if($row != 0) {
                $response['error'] = false;
                $response['sname'] = $row['shop_name'];
                $response['oname'] = $row['owner_name'];
                $response['date'] = $row['date_created'];
                $response['status'] = $row['status'];
                $response['gst'] = $row['tax_gst'];
                $response['oid'] = $row['owner_id'];
                $response['charges'] = $row['monthly_charges'];
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

    echo json_encode($response);
?>