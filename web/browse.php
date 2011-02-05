<?php

include('mysql.php');
include('cookieChecker.php');

$link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());

$sql = 'SELECT (flashcard_id, front) FROM flashcard WHERE user_id = ' . $user_info['user_id'];
$result = mysql_query($sql) or die(mysql_error());

echo "<html>\n<head>\n<title>Browse</title>\n</head>\n<body>\n";

while ($row = mysql_fetch_row($result)) {
  echo "<div>\n<div>$row[1]</div>\n<div><a href=\"edit.php?fcid=$row[1]\">Edit</a> | <a href=\"delete.php?fcid=$row[1]\">Edit</a></div>\n</div>";
}

echo "</body>\n</html>";
?>