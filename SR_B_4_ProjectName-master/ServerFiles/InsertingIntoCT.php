<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/InsertingIntoCT.php to run code
//an error 500 message when trying to run a php file on the server usually means there are errors in the code!

$host="mysql.cs.iastate.edu";
$port=3306;
$user="dbu309srb4";
$password="X@qA@bQ5";
$dbname="db309srb4";

//The file the code reads from.
$filename = 'IdFile.txt';
//opens the file to be read from
$myfile = fopen($filename, "r") or die("Unable to open file!");

//reads from the file (Should be an int only).
//Use IdFileResetter.php to reset the ID value to 0;
$readValue = fread($myfile,filesize('IdFile.txt'));

$user_ID=  $readValue;


//This value is recieved from the Android volley client
$user_Name = $_POST["userName"];
//This value is recieved from the Android volley client
$user_Password = $_POST["userPassword"];
$user_AdminID = 0;



// connects to database using the login credentials
$conn = mysqli_connect($host,$user,$password,$dbname,$port);

//tests and checks for a successful connection to the database
if (!$conn){
  die("Connection failed: ".mysqli_connect_error());
}
if($conn){
  //echo "Connection successful!";
}

//inserts into the credentials table
$sql = "INSERT INTO credentials(accountID,userName,userPassword,adminID) VALUES('$user_ID','$user_Name','$user_Password','$user_AdminID')";

mysqli_query($conn,$sql);

//opens the file to wrtie to.
$handle = fopen($filename, 'w') or die("Could not open file!");
//overWrites the IdFile to be the next user
fwrite($handle, $user_ID=$user_ID+1);

//closes the file
fclose($handle);
mysqli_close($conn);
?>
