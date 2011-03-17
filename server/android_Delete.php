<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass);
mysql_select_db("SS12",$link);

$q=mysql_query("DELETE FROM flashcard WHERE flashcard_id = '".$_REQUEST['flashcard_id']."'");
if($q){
	echo "[{\"result\":\"T\"}]"; 
}
else{
	echo "[{\"result\":\"F\"}]"; 
}
mysql_close();
?>