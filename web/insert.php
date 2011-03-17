<?php

include('../mysql.php');
include('../cookieChecker.php');
//declare vars
$front = mysql_real_escape_string($_POST['TextAreaFlashcardFront']);
$back = mysql_real_escape_string($_POST['TextAreaFlashcardBack']);
$fcID = $_POST['fcID'];
$group = mysql_real_escape_string($_POST['group']);
$newGroupName = mysql_real_escape_string($_POST['newGroupName']);
$public = $_POST['public'];

if($user_info) {
    //include functions.php for create/modify functions
    include('../functions.php');
    if($fcID == "") {
        echo("Creating new flashcard");
        //new flashcard
        //check for new group
        if($newGroupName != "" && $group == "new") {
            echo("<br />Creating new group<br />");
            $query = "INSERT INTO `group` (`group_id`, `user_id`, `name`, `public`) VALUES('', '$userid', '$newGroupName', '$public')";
            if(mysql_query($query)) {
                echo("Group created successfully<br />");
                //get group id
                $getGroupID = "SELECT * FROM `group` WHERE `name`='$newGroupName' AND `user_id`='$userid' LIMIT 1";
                $result = mysql_query($getGroupID);
                $rows = mysql_fetch_array($result);
                $group = $rows['group_id'];
            }
        }
        echo("<br />Creating THE FLASHCARD!!!<br />");
        $return = CreateFC($userid, $front, $back, $group);
        echo("<br />$return<br />"); 
        if($return) {
            echo("Flash card created successfully");
            echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index.php\">");
        } else {
            echo("Flash card not created");
        }
    } else {
        //modify/update flashcard
        if($newGroupName != "" && $group == "new") {
            $query = "INSERT INTO `group` (`group_id`, `user_id`, `name`, `public`) VALUES('', '$userid', '$newGroupName', '$public')";
            if(mysql_query($query)) {
                echo("Group created successfully<br />");
                //get group id
                $getGroupID = "SELECT * FROM `group` WHERE `name`='$newGroupName' AND `user_id`='$userid' LIMIT 1";
                $result = mysql_query($getGroupID);
                $rows = mysql_fetch_array($result);
                $group = $rows['group_id'];
            }
        }
        if(ModifyFC($fcID, $front, $back, $group)) {
            echo("Updated successfully");
            echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index.php\">");
        } else {
            echo("Not updated"); 
        }
    }
} else {
    echo("Not logged in");
}

?>