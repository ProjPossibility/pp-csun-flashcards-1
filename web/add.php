<?php

include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');

$friendid = $_GET['id'];

if(addFriend($friendid))
    echo("Friend has been added");
else
    echo("Friend was not added");

echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index.php\">");
    
?>

