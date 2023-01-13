<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $foodName = $_POST['foodName'];
  
    $query = "DELETE from `monan` 
        WHERE `foodname` ='".$foodName."'
    ";
    $data = mysqli_query($conn, $query);
   
    if($data)
    {
        $result=true;  
        print_r(json_encode($result));
    }
    else{
        $result=false;
        print_r(json_encode($result));
    }
  

}



