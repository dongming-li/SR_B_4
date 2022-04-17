<?php

// use http://proj-309-sr-b-4.cs.iastate.edu/UserRegister.php to run code
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

//This value is recieved from the UI game client
$user_Name = $_POST["N"];
/*$handle = fopen('CoordinateX.txt','w') or die("Could not open file");
fwrite($handle, $user_Name);
fclose($handle);*/
//This value is recieved from the UI game client
$user_Password = $_POST["P"];
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

$sql = "select e.userName from credentials e where e.userName = '$user_Name'";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));

$tmpArr = array();
$i=0;
while($row = mysqli_fetch_assoc($result)){
  $tmpArr[$i] = $row;
  $i++;
}

$count = count($tmpArr);

if($count==0){
  echo "You have successfully registered!";
  //able to register account
  $sql2 = "INSERT INTO credentials(accountID,userName,userPassword,adminID) VALUES('$user_ID','$user_Name','$user_Password','$user_AdminID')";
  $result2 = mysqli_query($conn,$sql2) or die("Error in selecting". mysqli_error($conn));
  //opens the file to wrtie to.
  $handle = fopen($filename, 'w') or die("Could not open file!");
  //overWrites the IdFile to be the next user
  fwrite($handle, $user_ID=$user_ID+1);
  fclose($handle);
}
else{
  //unable to register account
  echo "This username is already taken. Please try again with a new username.";
}

mysqli_close($conn);
?>
