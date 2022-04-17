<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/testDB.php to run code
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
  echo "Connection successful!";
}

//as a general rule of thumb if you dont need a mysqli_query statement comment it out along with code that goes with it.

//drops the credentials table that should have been created the last time this file ran check DB for table if errors
mysqli_query($conn,"DROP TABLE credentials");

//creates table to store user information
$sql = "CREATE TABLE credentials(
	accountID int,
	userName varchar(25),
    userPassword varchar(25),
    adminID int,
    primary key(accountID)
);";
mysqli_query($conn, $sql);


$aID = 0;//starts at 0; each new account will should be aID++ from the last account created
$userN = 'ntcarter';//gets from the login page of the UI
$userP ='@34btNNOP';//gets from the login page of the UI
$adminID = 4400011; //adminID's will need to be manually assigned




//inserts information into the credentials table of the database the single quotes around the variables are necessary
mysqli_query($conn, "INSERT INTO credentials(accountID,userName,userPassword,adminID) VALUES('$aID','$userN','$userP','$adminID')");

//closes the connection to the database
mysqli_close($conn);
?>
