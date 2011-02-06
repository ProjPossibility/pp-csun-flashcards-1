<?php

$front = $row['TextAreaFlashcardFront'];
$back = $row['TextAreaFlashcardBack'];


include('mysql.php');
include('cookieChecker.php');

if($user_info) {
    //logged in
    //bring up flash cards
    $query = "SELECT * FROM `flashcard` WHERE `user_id`='$userid'";
    $result = mysql_query($query);
    
    echo("<a href=\"createfc.php\">Create flashcard</a><br />");
    echo("To edit, please select from the following flashcards<ul>");
    while($row = mysql_fetch_array($result)) {
        echo("<li><a href=\"createfc.php?fc=" . $row['flashcard_id'] . "\">" . $row['front'] . "</a></li>");              
    }
    echo("</ul>");
} else {
    echo("Please login");
}

?>