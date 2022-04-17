<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/SendGetGameCoords.php to run code
//an error 500 message when trying to run a php file on the server usually means there are errors in the code!


$host="mysql.cs.iastate.edu";
$port=3306;
$user="dbu309srb4";
$password="X@qA@bQ5";
$dbname="db309srb4";

// connects to database using the login credentials
$conn = mysqli_connect($host,$user,$password,$dbname,$port);
//tests and checks for a successful connection to the database
if (!$conn){
  die("Connection failed: ".mysqli_connect_error());
}
if($conn){
  //echo "Connection successful!";
}

//These values are recieved from the UI game client
$user_Name = $_POST["N"];
$user_Password = $_POST["P"];
$user_Xcoord = $_POST["X"];
$user_Ycoord = $_POST["Y"];
$user_Direction = $_POST["D"];

$handle = fopen('CoordinateX.txt','w') or die("Could not open file");
fwrite($handle, $user_Xcoord);
fclose($handle);

$sql = "UPDATE game1 set facingDirection = '$user_Direction', coordX = '$user_Xcoord', coordY = '$user_Ycoord' where userName = '$user_Name'";
$sql2 = "select * from game1 e WHERE e.userName !='$user_Name'";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));
$result2 = mysqli_query($conn,$sql2) or die("Error in selecting". mysqli_error($conn));

$tmpArr = array();
while($row = mysqli_fetch_assoc($result2)){
  $checkUser = $row['userName'];
  $checkCoordX = $row['coordX'];
  $checkCoordY = $row['coordY'];
  $checkFD = $row['facingDirection'];
  $tmpArr[$i] = $row;
}

echo $checkUser." ".$checkCoordX." ".$checkCoordY." ".$checkFD;


mysqli_close($conn);
?>
