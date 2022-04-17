<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/Game1PlayerRequester.php to run code
//an error 500 message when trying to run a php file on the server usually means there are errors in the code!

$host="mysql.cs.iastate.edu";
$port=3306;
$user="dbu309srb4";
$password="X@qA@bQ5";
$dbname="db309srb4";

$user_Name = $_POST["N"];
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

$sql = "select * FROM game1";
//$sql = "select e.userName, e.userPassword from credentials e where e.userName = 'ntcarter'";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));

while($row = mysqli_fetch_assoc($result)) {
        echo  $row["accountID"]." ". $row["userName"]." ". $row["userPassword"]." ";
    }

mysqli_close($conn);

?>
