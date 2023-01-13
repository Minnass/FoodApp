<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $userName = $_POST['userName'];
    $passWord = $_POST['passWord'];
    $query = "SELECT `name`,`dateofbirth`,`sex`,`address`,`email`,`userID`,`loginType`,`role` from user
        WHERE `userName` ='".$userName."' and `passWord` ='".$passWord."' 
    ";
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_assoc($data);
    if($row)
    {
        $result =[
            'success'=>true,
            'user'=> $row
        ];

    }
    else{
        $result= [
            'success'=>false,
            'user'=>$row
        ];
    }
    print_r(json_encode($result));

}



