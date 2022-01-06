<?php
    require_once 'DbOperations_Admin.php';
    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        if(isset($_POST['sid'])) {
            
            $db = new DbOperations_Admin();

            $list = $db -> get_Charges($_POST['sid']);
            if($list != null) {
                while($row = $list -> fetch_assoc())
                {
                    $temp = array();
                    $temp['img'] = $row['recipt_name'];
                    $temp['date'] = $row['date'];
                    $temp['amt'] = $row['amount'];
                    array_push($response, $temp);
                }
            } else {
                $response['error'] = true;
                $response['message'] = "No Rows Found";
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