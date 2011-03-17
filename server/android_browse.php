<?php
include('mysql.php');
include('cookieChecker.php');
include('functions.php');
include('latex_convert.php');
include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');

$link = mysql_connect('localhost', $mysql_user, $mysql_pass);
mysql_select_db("SS12",$link);
 
$q=mysql_query("SELECT flashcard_id, front, back FROM flashcard WHERE user_id = '".$_REQUEST['user_id']."'");
while($e=mysql_fetch_assoc($q)){
	$e["front"]=mimetex_android($e["front"]);
	$e["back"]=mimetex_android($e["back"]);
//	$e = str_replace("[math]", "[math] \\reverse\\opaque \\LARGE ",$e);
       $output[]=$e;
 }
print(json_encode($output));
 
mysql_close();
?>