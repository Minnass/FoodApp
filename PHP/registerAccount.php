<?php
include "connect.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['type'];
    $userName = $_POST['userName'];
    $passWord = $_POST['passWord'];
    $email = $_POST['email'];

    if ($type == 'normal') {

        $query = "SELECT `name` from user
             WHERE `userName` ='" . $userName . "'";
        $data = mysqli_query($conn, $query);
        $row = mysqli_fetch_assoc($data);
        if ($row) {
            $result = [
                'success' => false,
                'message' => 'Tên đăng nhập đã tồn tại'
            ];
        } else {
            $query1 = "SELECT `name` from user
            WHERE `email` ='" . $email . "'";
            $data = mysqli_query($conn, $query1);
            $row = mysqli_fetch_assoc($data);
            if ($row) {
                $result = [
                    'success' => false,
                    'message' => 'Email đã được sử dụng'
                ];
            } else {

                $query3 = "INSERT INTO user (`userName`,`passWord`,`email`,`loginType`,`role`) VALUES ('" . $userName . "','" . $passWord . "','" . $email . "','" . $type . "','user')";

                mysqli_query($conn, $query3);

                $result = [
                    'success' => true,
                    'message' => "Đăng kí thành công"
                ];
            }
        }
        print_r(json_encode($result));
    } else if ($type == 'google') {
        $query = "SELECT `name` from user
        WHERE `email` ='" . $email . "'";
        $data = mysqli_query($conn, $query);
        $row = mysqli_fetch_assoc($data);
        if ($row) {
            $result = [
                'success' => false,
                'message' => 'Email da duoc su dung'
            ];
        } else {
            $query3 = "INSERT INTO user (`email`,`loginType`) VALUES ('" . $email . "','" . $type . "')";
            mysqli_query($conn, $query3);

            $result = [
                'success' => true,
                'message' => 'Đăng kí tài khoản mới'
            ];
        }
        print_r(json_encode($result));
    }
}
