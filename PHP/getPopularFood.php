<?php
include "connect.php";
$query = "SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`eaterNumber` FROM monan
            ORDER BY quantitysold DESC LIMIT 10
";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}
print_r(json_encode($result));
