<?php
include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $foodName = $_POST['foodName'];
    $image = $_POST['image'];
    $price = $_POST['price'];
    $discription = $_POST['discription'];
    $category=$_POST['category'];
    $quantitysold=$_POST['quantitysold'];
    $discount=$_POST['discount'];
    $eaterNumber=$_POST['eaterNumber'];
    $expiration=$_POST['expiration'];
    $preparationTime=$_POST['preparationTime'];
    $preservationGuide	=$_POST['preservationGuide'];

    $currentDate = new DateTime();
    $formatedCurrentDay=$currentDate->format('Y-m-d');
    $query ="INSERT INTO `monan` (`foodName`,`image`,`price`,`discription`,`category`,`quantitysold`,
    `discount`,`eaterNumber`,`expiration`,`preparationTime`,`preservationGuide`,`publishedDate`) 
     VALUES(
        '" . $foodName . "','" . $image . "','" . $price . "','" . $discription . "','" . $category . "'
        ,'" . $quantitysold . "','" . $discount . "','" . $eaterNumber . "','" . $expiration . "','" . $preparationTime . "'
        ,'" . $preservationGuide . "','".$formatedCurrentDay."'
    )";
    $data = mysqli_query($conn, $query);
    if($data)
    {
        $result= true;
        print_r(json_encode($result));
    }
    else{
        $result=true;
        print_r(json_encode($result));
    }
   
}

