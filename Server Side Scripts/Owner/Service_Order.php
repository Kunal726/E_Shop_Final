<?php
    require_once 'DbOperations_Owner.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['shopName']) and isset($_POST['owner'])) {
            
            $db = new DbOperations_Owner();
            $shopid = $db -> getShopID($_POST['owner'], $_POST['shopName']);
            $bill = $db -> getService($shopid);
            if($bill -> num_rows > 0) {
                while($row = $bill -> fetch_assoc())
                {
                    $temp = array();
                    $temp1 = array();
                    $temp['id'] = $row['order_id'];
                    $temp['addr'] = $row['user_address'].", ".$row['user_landmark'].", ".$row['user_city'];
                    $temp['price'] = $row['amount'];
                    $temp['time'] = $row['time'];
                    
                    $list = $db -> getList($row['order_id']);
                    if($list -> num_rows > 0) {
                        while($row1 = $list -> fetch_assoc())
                        {
                            $temp2 = array();
                            $temp2['sname'] = $row1['product_name'];
                            $temp2['sprice'] = (int) $row1['product_price'];
                            array_push($temp1, $temp2);
                        }
                    }
                    $temp['list'] = $temp1;
                    $temp['status'] = $row['order_status'];
                    $temp['tax'] = $db -> getTax($shopid);

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