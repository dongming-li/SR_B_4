<?php

$handle= fopen('CoordinateX.txt',"r") or die("Could not open X file!");
$readX= fread($handle, filesize('CoordinateX.txt'));
//echo "ReadX is ".$readX;
fclose($handle);

$handle = fopen('CoordinateY.txt',"r") or die("Could not open Y file");
$readY=fread($handle, filesize('CoordinateY.txt'));
//echo " readY is ".$readY;
fclose($handle);


$CoordObj -> x = $readX;
$CoordObj -> y = $readY;

echo json_encode($CoordObj);


?>
