<?php

//Use http://proj-309-sr-b-4.cs.iastate.edu/MovementTracker.php


//should recieve a String "x=SomeNumber&y=anotherNumber"
$Coord_X = $_POST["x"];
$Coord_Y = $_POST["y"];
//$testString = "x=4&y=66";

//prints for testing purposes
//echo "x is = ".$x. " and y = ".$y;


//writes the x coordinate to the specified file
$handle = fopen('CoordinateX.txt','w') or die("Could not open file");
fwrite($handle, $Coord_X);

fclose($handle);
//writes the y coordinate to the specified file
$handle = fopen('CoordinateY.txt','w') or die("Could not open file");
fwrite($handle, $Coord_Y);


//closes the files.
fclose($handle);

?>
