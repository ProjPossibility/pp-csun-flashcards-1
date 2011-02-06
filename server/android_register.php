<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass);
mysql_select_db("SS12",$link);
$username=$_REQUEST['user_name'];
$password=$_REQUEST['password'];
if(mysql_query("INSERT INTO `user` (`user_id`, `user_name`, `password`) VALUES (NULL, '$username', '$password')"))
{
	echo "T"; 
}
else{
	echo "F";
}
mysql_close();
?>