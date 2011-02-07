<?php

//use this file to determine if people are logged in
$userid = $_COOKIE['fcUserID'];
$password = $_COOKIE['fcPassword'];

$link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());

$query = "SELECT * FROM `user` WHERE `user_id`='$userid' LIMIT 1";

$result = mysql_query($query);

$user_info = mysql_fetch_array($result);

?>


