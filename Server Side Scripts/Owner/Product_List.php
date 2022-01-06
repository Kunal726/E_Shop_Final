<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['shopName']) and isset($_POST['owner'])) {
            
            $db = new DbOperations_Owner();
            $shopid = $db -> getShopID($_POST['owner'], $_POST['shopName']);
            $products = $db -> getProduct($shopid);
            if($products -> num_rows > 0) {
                while($row = $products -> fetch_assoc())
                {
                    $temp = array();
                    $temp['name'] = $row['product_name'];
                    $temp['image'] = $row['product_image'];
                    $temp['price'] = $row['product_price'];
                    $temp['qty'] = $row['product_quantity'];
                    $temp['id'] = $row['product_id'];
                    array_push($response, $temp);
                }
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

    echo json_encode($response);
?>