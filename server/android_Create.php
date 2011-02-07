<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass);
mysql_select_db("SS12",$link);
$sql = "SELECT * FROM `group` WHERE `user_id` = ".$_REQUEST['user_id']." AND `name` = '".$_REQUEST['name']."'";
$query=mysql_query($sql);
if($e=mysql_fetch_assoc($query)){
	$group_id = $e["group_id"];
}
else{
	$sql="INSERT INTO  `SS12`.`group` (`group_id` , `user_id` , `name` ) VALUES (NULL,'".$_REQUEST['user_id']."', '".$_REQUEST['name']."')";
	$q=mysql_query($sql);
	$group_id=mysql_insert_id() ;
}
if(mysql_query("INSERT INTO  `SS12`.`flashcard` (`flashcard_id` , `user_id` , `group_id` , `front` , `back` ) VALUES (NULL ,  '".$_REQUEST['user_id']."',  '".$group_id."',  '".$_REQUEST['front']."',  '".$_REQUEST['back']."')")){
	echo "[{\"result\":\"T\"}]"; 
}
else{
	echo "[{\"result\":\"F\"}]"; 
}
mysql_close();
?>