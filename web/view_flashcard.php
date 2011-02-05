<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html dir="ltr" xmlns="http://www.w3.org/1999/xhtml">

<!-- #BeginTemplate "master.dwt" -->

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<!-- #BeginEditable "doctitle" -->
<title>View Flashcard</title>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "header" -->
<div>
	<h1>View Flashcard</h1>
</div>
<!-- #EndEditable -->

<div id="navigation" align="center">
	<a href="index.html">HOME</a> | <a href="create_flashcard.html">CREATE</a> |
	<a href="view_flashcard.php">VIEW</a> | <a href="register.html">REGISTER</a> 
	| <a href="login.html">LOGIN</a><br />
</div>


<?php

include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());


  $query = "SELECT * FROM flashcard WHERE flashcard_id = 1";
  $result = mysql_query($query);
  while($row=mysql_fetch_row($result)){
  	echo $row['front'];
  }
?>


<!-- #BeginEditable "body" -->
<div>
	<br />
	<br />
</div>
<!-- #EndEditable -->

</body>

<!-- #EndTemplate -->

</html>
