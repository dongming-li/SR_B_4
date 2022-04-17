<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/LeavingLobby.php to run code
//an error 500 message when trying to run a php file on the server usually means there are errors in the code!

$host="mysql.cs.iastate.edu";
$port=3306;
$user="dbu309srb4";
$password="X@qA@bQ5";
$dbname="db309srb4";


//This value is recieved from the UI game client
$user_Name = $_POST["N"];
//This value is recieved from the UI game client
$user_Password = $_POST["P"];

// connects to database using the login credentials
$conn = mysqli_connect($host,$user,$password,$dbname,$port);
//tests and checks for a successful connection to the database
if (!$conn){
  die("Connection failed: ".mysqli_connect_error());
}
if($conn){
  //echo "Connection successful!";
}




$sql = "select e.userName, e.userPassword, e.accountID,e.adminID from game1 e where e.userName = '$user_Name'";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));
$tmpArr = array();
$i=0;
while($row2 = mysqli_fetch_assoc($result)){
  $tmpArr[$i] = $row2;
  $i++;
}

$count = count($tmpArr);

if($count==1){
  echo "Leaving Lobby";
  $sql2 = "delete FROM game1 where userName = '$user_Name'";
  mysqli_query($conn,$sql2) or die("Error in selecting". mysqli_error($conn));
}
else{
  echo "You are not in a lobby";
}


mysqli_close($conn);
?>
