<?php
	
	// $db="upload_db";
	// $host="localhost";
	// $pwd="";
	// $user="root";
	require_once  'dbConnection.php';

	$upload_path='image/';
	$server_ip=gethostbyname(gethostname());
	$upload_url='http://192.168.0.103/imgupload/'.$upload_path;
	$response = array();

	if ($_SERVER['REQUEST_METHOD'] == 'POST'){
		 if(isset($_POST['name'])and isset($_POST['email']) and isset($_FILES['image']['name']))
			{
				// $con=mysqli_connect($host,$user,$pwd,$db);

				// if(mysqli_connect_error($con)){ 
				// 	echo "Failed io Connect";
				// }
				
				$name=$_POST['name'];
				$email=$_POST['email'];
				$fileinfo=pathinfo($_FILES['image']['name']);
				$extension=$fileinfo['extension'];
				$file_url=$upload_url.'IMG_'.$name.'.'.$extension;
				$file_path=$upload_path.'IMG_'.$name.'.'.$extension;


				try{
				move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
				$sql = " INSERT INTO profile (name , email , photo ) VALUES ('$name','$email','$file_path')";

				if(mysqli_query($con,$sql))
				{
				$response['error']=false; 
				$response['name']=$name; 
				$response['email']=$email;

				}else{
					$response['error']=true; 
					// $response['value']=$file_url;
				}
			}catch(Exception $e){
				$response['error']=true; 
				$response['message']=$e->getMessage(); 
			}

			echo json_encode($response);
			
		}else{
			echo "isset failed";
		}
		mysqli_close($con);
	}else{
		echo "post failed";
	}
?>