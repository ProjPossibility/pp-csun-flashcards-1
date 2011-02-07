<?php
//Create a record
function CreateFC($user_id, $front, $back, $groupid){
  $sql = "INSERT INTO `flashcard` (`flashcard_id`, `user_id`, `group_id`, `front`, `back`) VALUES('', '$user_id', '$groupid', '$front', '$back')";
  return mysql_query($sql);
}

function ModifyFC($flashcard_id,$front, $back, $groupid){
  $sql = "UPDATE `flashcard` SET `front`='$front', `back`='$back', `group_id`='$groupid' WHERE `flashcard_id`='$flashcard_id'";
  return mysql_query($sql);      
}

function DeleteFC($flashcard_id){
  $sql = "DELETE FROM flashcard WHERE flashcard_id= ".$flashcard_id;
  return mysql_query($sql);
}

function ForceLogin() {
  global $user_info;

  if (!$user_info) {
    header('Location: http://www.calqlus.org/ss12/index.php');
    die('<html>
<head>
<title>Login Required</title>
<meta http-equiv="refresh" content="0;url=http://www.calqlus.org/ss12/index.php">
</head>
<body>Please login.</body>
</head>');
  }
}

?>