<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $userID= $_POST['userID'];
    $startDate = $_POST['startDate'];
    $endDate = $_POST['endDate'];
    $query ="SELECT `orderCode`,`orderDate`,`totalPrice`,`receivedUser`,`phoneNumber`,`address`
    ,`saleCode`,`deliveryType`,`paymentType`,
    `deliveryFee` FROM `order`
    WHERE `userID`= '".$userID."' and `orderDate` >= '".$startDate."' and `orderDate` <= '".$endDate."' ";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = ($row);
    }
    print_r(json_encode($result));
}
 


