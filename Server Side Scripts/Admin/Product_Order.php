<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['sid'])) {
            
            $db = new DbOperations_Admin();
            $shopid = $_POST['sid'];
            $bill = $db -> getBill($shopid);
            if($bill -> num_rows > 0) {
                while($row = $bill -> fetch_assoc())
                {
                    $temp = array();
                    $temp1 = array();
                    $temp['id'] = $row['order_id'];
                    $temp['address'] = $row['user_address'].", ".$row['user_landmark'].", ".$row['user_city'];
                    $temp['total'] = $row['amount'];
                    $temp['discount'] = $row['discount'];
                    $temp['finaltotal'] = $row['final_amount'];
                    
                    $list = $db -> getList($row['order_id']);
                    if($list -> num_rows > 0) {
                        while($row1 = $list -> fetch_assoc())
                        {
                            $temp2 = array();
                            $temp2['pname'] = $row1['product_name'];
                            $temp2['pqty'] = $row1['product_quantity'];
                            $temp2['pprice'] = ((int) $row1['product_quantity'] * (int) $row1['product_price']);
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