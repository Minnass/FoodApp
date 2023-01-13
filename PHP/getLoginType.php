<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $email = $_POST['email'];
    $query ="SELECT `loginType`  FROM user
    WHERE email = '".$email."'";
    $data = mysqli_query($conn, $query);
   
    $row = mysqli_fetch_assoc($data);
    print_r(json_encode($row));
}
 

