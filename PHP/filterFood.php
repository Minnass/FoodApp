<?php
include "connect.php";
$result="";
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $FirstKeyword = $_POST['FirstKeyword'];
    $CategoryKeyword=$_POST['CategoryKeyword'];
    $OptionKeyword=$_POST['OptionKeyword'];
    $query;
    if($OptionKeyword!='null'&&$CategoryKeyword!='null')
    {
        if($OptionKeyword=='Bán chạy')
        {
            $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
            WHERE foodname LIKE '%".$FirstKeyword."%' and `category` = '".$CategoryKeyword."' 
             ORDER BY `publishedDate` DESC LIMIT 6 ";    
        }
        else{
            $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
            WHERE foodname LIKE '%".$FirstKeyword."%' and `category` = '".$CategoryKeyword."' 
             ORDER BY `quantitysold` ASC LIMIT 3 ";    
        }
      
    }
    else
    {
        if($OptionKeyword!='null')
    {
        if($OptionKeyword=='Bán chạy')
        {
            $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
            WHERE foodname LIKE '%".$FirstKeyword."%'
             ORDER BY `publishedDate` DESC LIMIT 3 ";    
        }
        else{
       
            $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
            WHERE foodname LIKE '%".$FirstKeyword."%' 
             ORDER BY `quantitysold` ASC LIMIT 4 "; 
        }
    }
    if($CategoryKeyword!='null')
    {
        $query ="SELECT foodname,`price`,`image`,`discription`,`discount`,`quantitysold`,`category`,`eaterNumber` FROM monan
        WHERE foodname LIKE '%".$FirstKeyword."%' and `category`= '".$CategoryKeyword."'";
    }
    }
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
         $result[] = ($row);
}
 
}
print_r(json_encode($result));
