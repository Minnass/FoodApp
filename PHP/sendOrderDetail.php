<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $orderCode = $_POST['orderCode'];
    $foodName = $_POST['foodName'];
    $quantity = $_POST['quantity'];
    $price = $_POST['price'];
    $discount=$_POST['discount'];
    $image=$_POST['image'];
    $query ="INSERT INTO `orderdetail` (`orderCode`,`foodName`,`quantity`,`Price`,`discount`,`image`)
     VALUES(
        '".$orderCode."','".$foodName."','".$quantity."','".$price."','".$discount."','".$image."')";
   $data = mysqli_query($conn, $query);
    if($data)
    {
        $result= true;
        print_r(json_encode($result));
    }
    else{
        $result= false;
        print_r(json_encode($result));
    }
   
}
 

