<?php
include "connect.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $orderCode=$_POST['orderCode'];
    $query = "UPDATE `order` 
        SET `checked`= 1  where `orderCode`=  '" . $orderCode . "'";
    $data = mysqli_query($conn, $query);
    if ($data) {
        $result = true;
        print_r(json_encode($result));
    }
    else
    {
        $result = false;
        print_r(json_encode($result));
    }
}
