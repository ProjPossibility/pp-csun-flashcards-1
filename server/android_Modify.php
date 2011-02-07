<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass);
mysql_select_db("SS12",$link);
$sql="UPDATE `flashcard` SET `front` = '".$_REQUEST['front']."', `back` ='".$_REQUEST['back']."' WHERE `flashcard_id` = ".$_REQUEST['flashcard_id'];
if(mysql_query($sql))
{
	echo "[{\"result\":\"T\"}]"; 
}
else{
	echo "[{\"result\":\"F\"}]"; 
}
mysql_close();
?>