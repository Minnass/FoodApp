<?php
include "connect.php";
$result="";
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $search = $_POST['search'];
    $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
    WHERE foodname LIKE '%".$search."%'";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
         $result[] = ($row);
}
 
}
print_r(json_encode($result));
