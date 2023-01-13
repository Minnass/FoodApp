<?php
include "connect.php";
$result="";
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $search = $_POST['search'];
    $category=$_POST['category'];
    $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`eaterNumber` FROM monan
    WHERE foodname LIKE '%".$search."%' AND `category` ='".$category."'";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
         $result[] = ($row);
}
 
}
print_r(json_encode($result));
