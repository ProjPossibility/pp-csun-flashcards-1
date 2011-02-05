<?php
include('mysql.php');
$link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());
//$user_id = $_REQUEST('user_id');
$user_id= 1;
$flashcard_id= 1;
CreateFC($user_id,"FR","B");
ModifyFC($flashcard_id,"A","B");
$flashcard_id=11;
DeleteFC($flashcard_id);
//Create a record
function CreateFC($user_id,$front, $back){
  $sql = "INSERT INTO flashcard ( user_id, front, back) VALUES(".$user_id.", '".$front."', '".$back."' ) ";
  if(mysql_query($sql))
    echo "Create OK";
  
}
function ModifyFC($flashcard_id,$front, $back){
  $sql = "UPDATE flashcard SET front = '".$front."' , back='".$back."' WHERE flashcard_id = ".$flashcard_id;
  if(mysql_query($sql))
    echo "Modify is OK";  
}
function DeleteFC($flashcard_id){
  $sql = "DELETE FROM flashcard WHERE flashcard_id= ".$flashcard_id;
  echo $flashcard_id;
  if(mysql_query($sql))
    echo "DELETE is OK";  
}

?>