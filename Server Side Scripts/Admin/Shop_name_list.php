<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['adminid'])) {
            
            $db = new DbOperations_Admin();

            $list = $db -> getShop();
            if($list -> num_rows > 0) {
                while($row = $list -> fetch_assoc())
                {
                    $temp = array();
                    $temp['name'] = $row['shop_name'];
                    $temp['id'] = $row['shop_id'];
                    $temp['cat'] = $row['shop_category'];
                    array_push($response, $temp);
                }
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