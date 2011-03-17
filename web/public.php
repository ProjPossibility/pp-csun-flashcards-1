<?php

error_reporting(E_ALL);

include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');

$id = $_GET['id'];
$groupID = $_GET['groupID'];

if($id == '0') {
	$result = lockGroup($groupID);
	Echo("Locking Group<br />");
} else {
	$result = unlockGroup($groupID);
	Echo("Unlocking Group<br />");
}

if($result) {
	echo("Edits have been made");
} else {
	echo("Edits have not been made");
}

echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index1.php\">");

?>