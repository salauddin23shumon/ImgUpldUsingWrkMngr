<?php

 // uploading file to the server using unchanged file name

require_once  'dbConnection.php';
//this is our upload folder
$upload_path = 'image/';
 
//server url
$upload_url = 'http://192.168.0.103/imgupload/';
 
//response array
$response = array();
 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
 	
    //checking the required parameters from the request
    if(isset($_FILES['file']['name'])){
 
        $fileinfo = pathinfo($_FILES['file']['name']);

        $extension = $fileinfo['extension'];

        $file_path = $upload_path . basename($_FILES['file']['name']);

        $path = $upload_url . $file_path;
        
 
        //trying to save the file in the directory
        try{
            //saving the file         
            if(move_uploaded_file($_FILES['file']['tmp_name'], $file_path)){
                $sql = "INSERT INTO media ( file_path) VALUES ( '$path');";
     
                if(mysqli_query($con,$sql)){

                    //filling response array with values
                    $response['error'] = false;   
                    $response['row']	= getLastId($con);
                   
                }else{
                    $response['error'] = "query not success";
                }
            }else{
                $response['error'] = "not moved";
                $response['path'] = $file_url;
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
so this method will return a file name for the file to be upload
*/
	function getLastId($con){

	 	$query = "SELECT COUNT(id) FROM media";
		$result = mysqli_query($con,$query);
		$row = mysqli_fetch_row($result)[0];	
	   	return ++$row;
	}
?>
 