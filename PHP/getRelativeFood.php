<?php
include "connect.php";
$result;
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $foodName = $_POST['foodName'];
    $query = "SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`eaterNumber` FROM monan m
        WHERE ( m.category = (SELECT category FROM monan WHERE foodname='".$foodName."') or m.foodName like '$".$foodName."$' )
        and m.foodName !='".$foodName."'
    ";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = ($row);
    }
 
}
print_r(json_encode($result));

