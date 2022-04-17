<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/InsertIntoGameLobby.php to run code
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

$sql = "select e.userName, e.userPassword, e.accountID,e.adminID from credentials e where e.userName = '$user_Name'";
//$sql = "select e.userName, e.userPassword, e.accountID,e.adminID from credentials e where e.userName = 'demo'";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));

while($row = mysqli_fetch_assoc($result)){
  $checkUser = $row['userName'];
  $CheckPassword = $row['userPassword'];
  $CheckAccountID = $row['accountID'];
  $CheckAdminID = $row['adminID'];
}

$sql2 = "select * from game1";
$result2 = mysqli_query($conn,$sql2) or die("Error in selecting". mysqli_error($conn));

$tmpArr = array();
$i=0;
while($row2 = mysqli_fetch_assoc($result2)){
  $tmpArr[$i] = $row2;
  $i++;
}

$count = count($tmpArr);
if($count>=2){
  echo "This lobby is full";
}
if($count<2){
  echo "Joining lobby";
  $sql3 = "INSERT INTO game1(accountID,userName,userPassword,adminID) VALUES('$CheckAccountID','$checkUser','$CheckPassword','$CheckAdminID')";
  mysqli_query($conn,$sql3) or die("Error in selecting". mysqli_error($conn));
}

mysqli_close($conn);
?>
