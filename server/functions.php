<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());

//Create a record
function CreateFC($user_id,$front, $back){
  $sql = "INSERT INTO flashcard ( user_id, front, back) VALUES(".$user_id.", '".$front."', '".$back."' ) ";
  mysql_query($sql);
  
}
function ModifyFC($flashcard_id,$front, $back){
  $sql = "UPDATE flashcard SET front = '".$front."' , back='".$back."' WHERE flashcard_id = ".$flashcard_id;
  mysql_query($sql);      
}
function DeleteFC($flashcard_id){
  $sql = "DELETE FROM flashcard WHERE flashcard_id= ".$flashcard_id;
  mysql_query($sql);
}

?>