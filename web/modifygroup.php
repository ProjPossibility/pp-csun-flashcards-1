<?php

include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');

//declare vars
$groupName = $_POST['groupName'];
$public = $_POST['public'];
$groupID = $_POST['groupID'];
$add = $_POST['add'];
$delID = $_GET['id'];

if($user_info) {
	if($delID == -1) {
		//delete group
		//echo("groupid is: $groupID<br />");
		$sql = "DELETE FROM `group` WHERE `group_id`=$groupID";
		$result = mysql_query($sql);
		if($result) {
			echo("Deletion successful");
		} else {
			echo("Deletion was not successful");
		}
	} //delete the group
	
	if($add == 1) {
		$sql = "INSERT INTO `group` (`group_id`, `user_id`, `name`, `public`) VALUES('', '$userid', '$groupName', '$public')";
		$result = mysql_query($sql);
		if($result) {
			echo("Added new group");
		} else {
			echo("Could not add new group");
		}
		//add new group
	} else {
		$sql = "UPDATE `group` SET `name`='$groupName', `public`='$public' WHERE `group_id`=$groupID";
		$result = mysql_query($sql);
		if($result) {
			echo("Group Updated");
		} else {
			echo("Could not update group");
		}
	}
	
	echo('<html>
<head>
<title>Login Required</title>
<meta http-equiv="refresh" content="0;url=http://www.calqlus.org/ss12/index.php">
</head>
<body>Please login.</body>
</head>');
} else {
    echo("Not logged in");
	echo('<html>
<head>
<title>Login Required</title>
<meta http-equiv="refresh" content="0;url=http://www.calqlus.org/ss12/index.php">
</head>
<body>Please login.</body>
</head>');
}

?>