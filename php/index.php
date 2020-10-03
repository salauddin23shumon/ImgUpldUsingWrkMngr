<?php
if(isset($_FILES['file']['name'])){
    $result = array("success" => $_FILES['file']['name']);
    $file_path = basename($_FILES['file']['name']);
    if(move_uploaded_file($_FILES['file']['tmp_name'], $file_path)){
        $result = array("success" => "file uploaded");
    }else{
        $result = array("success" => "failed");
    }
}else{
    $result = array("success" => "No file received");
}
echo json_encode($result, JSON_PRETTY_PRINT);
?>