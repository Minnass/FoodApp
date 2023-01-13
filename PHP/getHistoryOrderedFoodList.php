<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $orderCode = $_POST['orderCode'];
    $query ="SELECT `foodName`,`quantity`,`Price`,`discount`,`image`
    FROM `orderdetail`
    WHERE `orderCode` = '".$orderCode."' ";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = ($row);
    }
    print_r(json_encode($result));
}
 