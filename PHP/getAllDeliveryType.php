<?php
include "connect.php";
$query = "SELECT `name`,`icon`,`fee`,`description` FROM deliveryform";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}

print_r(json_encode($result)); 
?>