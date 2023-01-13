<?php
include "connect.php";


$query = "select max(id) as id from `monan`";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}
if ($result[0]['id'] == null) {
    $name = 1;
} else {
    $name = ++$result[0]['id'];
}
 
$name = $name . ".jpg";


if (isset($_FILES["file"])) {
    $target_dir = "images/";
    $target_file_name = $target_dir.$name;
    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)) {
      $arr=[
        'success'=>true,
        'imageName'=>$name
      ];
    } else {
        $arr=[
            'success'=>false,
            'imageName'=>$name
          ];
    }

} else {
    $arr=[
        'success'=>false,
        'imageName'=>$name
      ];
 
}
print_r(json_encode($arr));
