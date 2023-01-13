<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $foodName = $_POST['foodName'];
    $query ="SELECT `eaterNumber`,`expiration`,`preparationTime`,`preservationGuide`  FROM monan
    WHERE foodname = '".$foodName."'";
    $data = mysqli_query($conn, $query);
   
    $row = mysqli_fetch_assoc($data);
    print_r(json_encode($row));
}
 

