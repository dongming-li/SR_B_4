<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/RetrieveCT.php to run code
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

//selects all the data from the credentials table
$sql = "select * from credentials";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));

$tmpArr = array();
$i=0;
while($row = mysqli_fetch_assoc($result)){
  $tmpArr[$i] = $row;
  $i++;
}

echo json_encode($tmpArr);

mysqli_close($conn);
?>
