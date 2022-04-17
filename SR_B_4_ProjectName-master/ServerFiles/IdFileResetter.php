<?php
//Run this file at http://proj-309-sr-b-4.cs.iastate.edu/IdFileResetter.php
$filename = 'IdFile.txt';
$i = 0;



//overWrites the file to be $i only;
$handle = fopen($filename, 'w') or die("Could not open file!");
fwrite($handle, $i);
//echo $i." Has been written to the file   ";



$myfile = fopen($filename, "r") or die("Unable to open file!");
$readValue = fread($myfile,filesize('IdFile.txt'));
//echo "The read Value = ".$readValue."     ";

fclose($handle);
?>
