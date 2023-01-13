<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $userID = $_POST['userID'];
    $orderCode = $_POST['orderCode'];
    $orderDate = $_POST['orderDate'];
    $totalPrice = $_POST['totalPrice'];
    $receivedUser=$_POST['receivedUser'];
    $phoneNumber=$_POST['phoneNumber'];
    $address=$_POST['address'];
    $saleCode=$_POST['saleCode'];
    $deliveryType=$_POST['deliveryType'];
    $paymentType=$_POST['paymentType'];
    $deliveryFee=$_POST['deliveryFee'];
    $query ="INSERT INTO `order` (`userID`,`orderCode`,`orderDate`,`totalPrice`,`receivedUser`,`phoneNumber`,
    `address`,`saleCode`,`deliveryType`,`paymentType`,`deliveryFee`,`checked`) 
     VALUES(
        '" . $userID . "','" . $orderCode . "','" . $orderDate . "','" . $totalPrice . "','" . $receivedUser . "'
        ,'" . $phoneNumber . "','" . $address . "','" . $saleCode . "','" . $deliveryType . "','" . $paymentType . "'
        ,'" . $deliveryFee . "',0
    )";
    $data = mysqli_query($conn, $query);
    if($data)
    {
        $result= true;
        print_r(json_encode($result));
    }
    else{
        $result=true;
        print_r(json_encode($result));
    }
   
}
 

