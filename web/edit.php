<?php

//check passed in to create or edit
$fcID = $_GET['fcid'];

include('mysql.php');
include('cookieChecker.php');
?>

<html>
<head>
<title>FlashCard Editor</title>
</head>
<body>
<?php
if($user_info) {
    $fcFront = "";
    $fcBack = "";
    if($fcID != "") {
        $query = "SELECT * FROM `flashcard` WHERE `flashcard_id`='$fcID' LIMIT 1";
        $result = mysql_query($query);
        $row = mysql_fetch_array($result);
        $fcFront = $row['front'];
        $fcBack = $row['back'];
    }
    echo("<form action=\"insert.php\" method=\"POST\">");
    echo("Front:<br />");
    echo("<textarea cols=\"20\" name=\"TextAreaFlashcardFront\" rows=\"2\">$fcFront</textarea>");
    echo("<br />Back:<br />");
    echo("<textarea cols=\"20\" name=\"TextAreaFlashcardBack\" rows=\"2\">$fcBack</textarea>");
    echo("<input type=\"hidden\" name=\"fcID\" value=\"$fcID\" />");
    echo("<br />");
    echo("<input type=\"submit\" value=\"Submit\" />");
    echo("</form>");

} else {
    echo("Please login");
}

?>
</body>
</html>  
    

