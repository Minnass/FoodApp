<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $email = $_POST['email'];
    $oldPassword=$_POST['oldPassword'];
    $newPassword = $_POST['newPassword'];
    $query = "SELECT `name` from user
        WHERE `email` ='".$email."' and `passWord` ='".$oldPassword."' 
    ";
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_assoc($data);
    if($row)
    {
        $query1="UPDATE `user` SET `passWord`='".$newPassword."' where `email`='".$email."'";
        $run = mysqli_query($conn, $query1);
        $result =[
            'success'=>true,
            'message'=>"Thay đổi mật khẩu thành công"
        ];

    }
    else{
        $result= [
            'success'=>false,
            'message'=>"Mật khẩu cũ không chính xác"
                ];
    }
    print_r(json_encode($result));

}



