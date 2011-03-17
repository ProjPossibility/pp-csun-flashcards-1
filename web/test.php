<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php
include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');
include('../latex_convert.php');
include('../parse_math.php');
include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');

$groupx = $_GET['groupID'];

ForceLogin();
    $sql = "SELECT * FROM `flashcard` WHERE `group_id`='$groupx'";
    $result = mysql_query($sql) or die(mysql_error());

$currentDate = date("ymdhis",time());
list($usecs,$secs) = microtime();
$fileName = "{$currentdate}{$usecs}";


$stringToSend = "Now playing" . $row['name'];
$firstTime = 1;
$cardNumber = 1;

while($row = mysql_fetch_array($result)) {
	if( $firstTime == 1 )
	{
		$stringToSend = "Now playing " . $row['name'];
		$stringToSend =  $stringToSend . " set of flash cards ";
		$firstTime = 0;
	}
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend =  $stringToSend . " a1s ";

	$stringToSend =  $stringToSend . "Flash card number ";
	$stringToSend =  $stringToSend . $cardNumber;
	$stringToSend = $stringToSend . ' ';
	$cardNumber++;
	
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend =  $stringToSend . " a1s ";

	$stringToSend =  $stringToSend . "Front of Card  ";
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend = $stringToSend . $row['front'];
	$stringToSend = $stringToSend . ' ';
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend =  $stringToSend . "Back of Card  ";
	$stringToSend =  $stringToSend . " a1s ";
	$stringToSend = $stringToSend . $row['back'];
	$stringToSend = $stringToSend . ' ';
	
}
echo($stringToSend);

echo("<a href=\"http://hackroute.com/redirect.php?tts=$stringToSend\" target=\"_blank\"><img src=\"images/mp3.jpg\" alt=\"Press hear to download sound file\" title=\"Press hear to download sound file\" style=\"border: 0px\" /></a>"); 


?>
