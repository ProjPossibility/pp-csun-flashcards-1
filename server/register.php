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
    $query = "SELECT * FROM `user` WHERE `user_name`='$username' LIMIT 1";
    $result = mysql_query($query);
    $row = mysql_fetch_array($result);
    echo("Account created successfull<br />Logging in now...");
    $expire = time() + 60 * 60 * 24 * 30;
    //userid cookie
    setcookie("fcUserID", $row['user_id'], $expire); 
    setcookie("fcPassword", $row['password'], $expire);
    $user_info = $row;
    echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index.php\">");
} else {
    echo("Error creating account");
}

mysql_close($link);

?>


