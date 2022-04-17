<?php
// use http://proj-309-sr-b-4.cs.iastate.edu/UserLogin.php to run code
//an error 500 message when trying to run a php file on the server usually means there are errors in the code!

$host="mysql.cs.iastate.edu";
$port=3306;
$user="dbu309srb4";
$password="X@qA@bQ5";
$dbname="db309srb4";

//This value is recieved from the UI game client
$user_Name = $_POST["N"];
/*$handle = fopen('CoordinateX.txt','w') or die("Could not open file");
fwrite($handle, $user_Name);
fclose($handle);*/
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
//$sql = "select e.userName, e.userPassword from credentials e where e.userName = 'ntcarter'";
$result = mysqli_query($conn,$sql) or die("Error in selecting". mysqli_error($conn));



$tmpArr = array();
$i=0;
while($row = mysqli_fetch_assoc($result)){
  $checkUser = $row['userName'];
  $CheckPassword = $row['userPassword'];
  $CheckAccountID = $row['accountID'];
  $CheckAdminID = $row['adminID'];
  $tmpArr[$i] = $row;
  $i++;
}

//checks to make sure only 1 username was returned. If there are more then 1 or less then 1 something went wrong.
$count = count($tmpArr);

//the attempted username doesnt exist in the database or the passwords doesnt match (failed login)
if($count==0 || $CheckPassword !=$user_Password){
  echo "Incorrect username or password please try again.";
}
//the user exists and matches the username and password and Username isnt empty (Successful Login)
if($count=1 &&$CheckPassword ==$user_Password && $checkUser ==$user_Name&&$user_Name!=""&&$user_Password!=""){
  echo "Login Successful!"." ".$checkUser." ".$CheckPassword." ".$CheckAccountID." ".$CheckAdminID;
}
//Two of the same username Exists on the database (THIS SHOULD NEVER HAPPEN).
if($count>1){
  echo "Something went wrong please try again or contact an admistrator";
}
mysqli_close($conn);
?>
