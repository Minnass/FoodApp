<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $email = $_POST['email'];
    $query = "SELECT * from `user`
        WHERE `email` ='".$email."'  
    ";
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_assoc($data);
    if($row)
    {
        $result =true;
    }
    else{
        $query1 = "INSERT INTO user (`userName`,`passWord`,`email`,`loginType`,`role`) VALUES ('','','" . $email . "','google','user')";

                mysqli_query($conn, $query1);

        $result=false;
    }
    print_r(json_encode($result));

}



