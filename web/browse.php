<?php

include('mysql.php');
include('cookieChecker.php');

if (!$user_info)
  die("Please log in.");

$sql = 'SELECT flashcard_id, front FROM flashcard WHERE user_id = ' . $user_info['user_id'];
$result = mysql_query($sql) or die(mysql_error());

echo "<html>\n<head>\n<title>Browse</title>\n</head>\n<body>\n";
echo "<a href=\"edit.php\">Create New</a><br />";
if (mysql_num_rows($result)) {
  while ($row = mysql_fetch_row($result)) {
    echo "<div>\n<div>$row[1]</div>\n<div><a href=\"edit.php?fcid=$row[0]\">Edit</a> | <a href=\"delete.php?fcid=$row[0]\">Delete</a></div>\n</div>";
  }
} else {
  echo "You have no flashcards.";
}

echo "</body>\n</html>";
?>