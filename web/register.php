<?php

//identify passed in variables
$username = mysql_real_escape_string($_POST['username']);
$password = md5($_POST['password']);

include('mysql.php');

$link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());

$query = "INSERT INTO `user` (`user_id`, `user_name`, `password`) VALUES (NULL, '$username', '$password')";

$result = mysql_query($query) or die(mysql_error());
if($result) {
    echo("Account created successfull");
} else {
    echo("Error creating account");
}

mysql_close($link);

?>


