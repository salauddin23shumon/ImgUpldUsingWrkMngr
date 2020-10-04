<?php

// uploading file to the server by generating new file name
 
//importing dbDetails file
define('DB_HOST','localhost');
define('DB_USERNAME','root');
define('DB_PASSWORD','');
define('DB_NAME','upload_db');
 
//this is our upload folder
$upload_path = 'image/';
 
//Getting the server ip
$server_ip = gethostbyname(gethostname());
 
//creating the upload url
$upload_url = 'http://192.168.0.103/imgupload/'.$upload_path;
 
//response array
$response = array();
 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    //checking the required parameters from the request
    if(isset($_POST['name']) and isset($_POST['email']) and isset($_FILES['image']['name'])){
 
        //connecting to the database
        $con = mysqli_connect(DB_HOST,DB_USERNAME,DB_PASSWORD,DB_NAME) or die('Unable to Connect...');
 
        //getting name from the request
        $name = $_POST['name'];
        $email = $_POST['email'];
 
        //getting file info from the request
        $fileinfo = pathinfo($_FILES['image']['name']);
 
        //getting the file extension
        $extension = $fileinfo['extension'];
 
        //file url to store in the database
        $file_url = $upload_url . getFileName() . '.' . $extension;
 
        //file path to upload in the server
        $file_path = $upload_path . getFileName() . '.'. $extension;
 
        //trying to save the file in the directory
        try{
            //saving the file
            // move_uploaded_file($_FILES['image']['tmp_name'],$file_path);

            if(move_uploaded_file($_FILES['image']['tmp_name'], $file_path)){
                $sql = "INSERT INTO profile ( name , email , photo) VALUES ( '$name', '$email', '$file_url');";
     
                //adding the path and name to database

                if(mysqli_query($con,$sql)){
     
                    //filling response array with values
                    $response['error'] = false;
                    // $response['url'] = $file_url;
                    $response['name'] = $name;
                }else{
                    $response['error'] = "query not success";
                }
            }else{
                $response['error'] = "not moved";
            }
            //if some error occurred
        }catch(Exception $e){
            $response['error']=true;
            $response['message']=$e->getMessage();
        } 
        //closing the connection
        mysqli_close($con);
    }else{
        $response['error']=true;
        $response['message']='Please choose a file';
    }
    
    //displaying the response
    echo json_encode($response);
}
 
/*
We are generating the file name
so this method will return a file name for the image to be upload
*/
function getFileName(){
    $con = mysqli_connect(DB_HOST,DB_USERNAME,DB_PASSWORD,DB_NAME) or die('Unable to Connect...');
    $sql = "SELECT max(id) as id FROM profile";
    $result = mysqli_fetch_array(mysqli_query($con,$sql));
 
    mysqli_close($con);
    if($result['id']==null)
        return 1;
    else
        return ++$result['id'];
}

    function getLastId($con){

        $query = "SELECT COUNT(id) FROM media";
        $result = mysqli_query($con,$query);
        $row = mysqli_fetch_row($result)[0];    
        return ++$row;
    }
?>
 