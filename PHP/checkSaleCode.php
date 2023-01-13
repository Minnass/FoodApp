<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $code = $_POST['code'];
    $query = "SELECT `code`,`codeName`,`discountValue` from salecode
        WHERE `code` ='".$code."'  
    ";
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_assoc($data);
    if($row)
    {
        $result =[
            'saleCodeModel'=> $row,
            'successful'=>true
        ];

    }
    else{
        $result= [
            'saleCodeModel'=> $row,
            'successful'=>false
        ];
    }
    print_r(json_encode($result));

}



