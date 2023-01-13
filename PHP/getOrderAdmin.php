<?php
include "connect.php";

    $query ="SELECT `orderCode`,`orderDate`,`totalPrice`,`receivedUser`,`phoneNumber`,`address`
    ,`saleCode`,`deliveryType`,`paymentType`,
    `deliveryFee` FROM `order`
    WHERE `checked`=0";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = ($row);
    }
    print_r(json_encode($result));
