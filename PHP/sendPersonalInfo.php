<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $email = $_POST['email'];
    $name=$_POST['name'];
    $dateofbirth=$_POST['dateofbirth'];
    $sex=$_POST['sex'];
    $address=$_POST['address'];

    $query ="UPDATE `user` 
        SET `name`= '" . $name . "' ,`dateofbirth`='" . $dateofbirth . "',`sex`='" . $sex . "',`address`='" . $address . "'
        where `email`=  '" . $email . "'";
    $data = mysqli_query($conn, $query);
    if($data)
    {
        $result= true;
        print_r(json_encode($result));
    }
    else{
       $result=false;
        print_r(json_encode($result));
    }
   
}
 

