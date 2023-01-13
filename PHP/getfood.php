<?php
include "connect.php";
$query = "SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`eaterNumber` FROM monan";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}
print_r(json_encode($result)); 
?>