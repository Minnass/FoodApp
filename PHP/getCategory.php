<?php
include "connect.php";
$result="";
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $category = $_POST['category'];
    $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
    WHERE category = '".$category."'";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
         $result[] = ($row);
}
}
print_r(json_encode($result));
