<?php

use PHPMailer\PHPMailer\PHPMailer;

include "connect.php";

require("PHPMailer-master/src/PHPMailer.php");
require("PHPMailer-master/src/Exception.php");
require("PHPMailer-master/src/SMTP.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email = $_POST['email'];
    $query = "SELECT `name`,`dateofbirth`,`sex`,`address`,`email`,`userID`,`loginType` from user
    WHERE `email` ='" . $email . "'";
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_assoc($data);
    if ($row) {
        $n=10;
        $randomString = uniqid();
        $mail = new PHPMailer();
        $mail->isSMTP();
        $mail->CharSet = 'UTF-8';
        $mail->SMTPAuth = true;
        $mail->SMTPSecure = 'ssl';
        $mail->Host = "smtp.gmail.com";
        $mail->Port = 465;
        $mail->isHTML(true);
        $mail->Username = "phannhattrieu012@gmail.com";
        $mail->Password = "fcexwnqciquyrcuv";
        $mail->setFrom("phannhattrieu012@gmail.com");
        $mail->Subject = "Mật khẩu hiện tại của bạn";
        $mail->Body = $randomString;
        $mail->addAddress($email);
        if ($mail->send()) {
            $query1="UPDATE `user` SET `passWord`='".$randomString."' where `email`='".$email."'";
            $run = mysqli_query($conn, $query1);
            $result = true;
            print_r(json_encode($result));

        }
        else{
            $result = false;
            print_r(json_encode($result));    
        }
    } else {
        $result = false;
        print_r(json_encode($result));
      
    }
}

// }
