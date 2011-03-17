<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass);
mysql_select_db("SS12",$link);
 
$q=mysql_query("SELECT * FROM user WHERE user_id = '".$_REQUEST['user_id']."'");
while($e=mysql_fetch_assoc($q))
        $output[]=$e;
 
print(json_encode($output));
 
mysql_close();
?>