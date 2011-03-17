<?php
error_reporting(E_ALL);
//include('latex_convert.php');
//include('parse_math.php');
//include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');


//Create a record
function CreateFC($user_id, $front, $back, $groupid) {
  $sql = "INSERT INTO `flashcard` (`flashcard_id`, `user_id`, `group_id`, `front`, `back`) VALUES('', '$user_id', '$groupid', '$front', '$back')";
  $result = mysql_query($sql);
  //insert flaschard into db
  if($result) {
    //echo("<br />Flashcard has been inserted");
    //get that flashcards id
    $sql = "SELECT * FROM `flashcard` WHERE `user_id`='$user_id' ORDER BY `flashcard_id` DESC LIMIT 1";
    $fcIDResult = mysql_query($sql);
    if($fcIDResult) {
        $row = mysql_fetch_array($fcIDResult);
        $fcID = $row['flashcard_id'];
        
        //echo("<br />Parsing Strings: $front<br />");
        //parse string into individual words 
        $tagsFront = strtok($front, ' ');
        $tagsFrontArray = array();
        while($tagsFront !== false) {
            //check for filler words like is, what, the, etc
            if(checkWord($tagsFront))
                $tagsFrontArray[] = $tagsFront;
            $tagsFront = strtok(' ');
        }
        //print_r($tagsFrontArray);
        
        //echo("<br />Parsing Strings: $back<br />");
        $tagsBack = strtok($back, ' ');
        $tagsBackArray = array();
        while($tagsBack !== false) {
            //check for filler words like is, what, the, etc
            if(checkWord($tagsBack))
                $tagsBackArray[] = $tagsBack;
            $tagsBack = strtok(' ');
        }
        //print_r($tagsBackArray);
        
        //echo("<br />Inserting Strings");
        for($i = 0; $i < count($tagsFrontArray); $i++) {
            //echo("<br />Inserting Tags");
            $tag = $tagsFrontArray[$i];
            $sql = "INSERT INTO `tags` (`tag_id`, `fc_id`, `tag`) VALUES('', '$fcID', '$tag')";  
            $result1 = mysql_query($sql) or die(mysql_error());
            //echo("<br />Result is: $result1");
        }
        
        for($i = 0; $i < count($tagsBackArray); $i++) {
            $sql = "INSERT INTO `tags` (`tag_id`, `fc_id`, `tag`) VALUES('', '$fcID', '$tagsBackArray[$i]')";  
            mysql_query($sql);
        }
    }
    return true;
  }
  return false;
}

function checkWord($word) {
    if(strcmp($word, "the") == 0 || strcmp($word, "is") == 0 || strcmp($word, "a") == 0 || strcmp($word, "The") == 0 || strcmp($word, "Is") == 0 || strcmp($word, "A") == 0 || strcmp($word, "an") == 0 || strcmp($word, "An") == 0 || strcmp($word, "I") == 0 || strcmp($word, "you") == 0 || strcmp($word, "You") == 0 || strcmp($word, "you're") == 0 || strcmp($word, "what") == 0 || strcmp($word, "What") == 0) {
        return false;
    }
        return true;
}

function ModifyFC($flashcard_id,$front, $back, $groupid){
    //insert flashcard into db
  $sql = "UPDATE `flashcard` SET `front`='$front', `back`='$back', `group_id`='$groupid' WHERE `flashcard_id`='$flashcard_id'";
  $result = mysql_query($sql);      
  
  //find old tags and delete them
  $sql = "DELETE FROM `tags` WHERE `fc_id`='$flashcard_id'";
  mysql_query($sql);
  
  //create new tags
  $tagsFront = strtok($front, ' ');
  $tagsFrontArray = array();
  while($tagsFront !== false) {
    //check for filler words like is, what, the, etc
    if(checkWord($tagsFront))
         $tagsFrontArray[] = $tagsFront;
    $tagsFront = strtok(' ');
  }
  
  $tagsBack = strtok($back, ' ');
  $tagsBackArray = array();
  while($tagsBack !== false) {
      //check for filler words like is, what, the, etc
      if(checkWord($tagsBack))
           $tagsBackArray[] = $tagsBack;
      $tagsBack = strtok(' ');
  }
  
  for($i = 0; $i < count($tagsBackArray); $i++) {
      $sql = "INSERT INTO `tags` (`tag_id`, `fc_id`, `tag`) VALUES('', '$flashcard_id', '$tagsBackArray[$i]')";  
      mysql_query($sql);
  }
  
  for($i = 0; $i < count($tagsFrontArray); $i++) {
      $sql = "INSERT INTO `tags` (`tag_id`, `fc_id`, `tag`) VALUES('', '$flashcard_id', '$tagsFrontArray[$i]')";  
      mysql_query($sql);
  }
  
  return $result;
}

function DeleteFC($flashcard_id){
  $sql = "DELETE FROM flashcard WHERE flashcard_id= ".$flashcard_id;
  return mysql_query($sql);
}


function lockGroup($groupID) {
  $sql = "UPDATE `group` SET `public`='0' WHERE `group_id`='$groupID'";
  return mysql_query($sql) or die(mysql_error());
}

function unlockGroup($groupID) {
  $sql = "UPDATE `group` SET `public`='1' WHERE `group_id`='$groupID'";
  return mysql_query($sql) or die(mysql_error());
}

function searchUsers($keyword) {
	$sql = "SELECT * FROM `user` WHERE `user_name` LIKE '$keyword'";
	$result = mysql_query($sql) or die(mysql_error());
	$text = "";
	if($result) {
		while($row = mysql_fetch_array($result)) {
			//$text .= $row['user_name'] . "<br />";
			$text .= "<li><a href=\"profile.php?id=" . $row['user_id'] . "\">" . $row['user_name'] . "</a></li>";
		}
		//$text= "Results";
	} else {
		$text = "No results";
	}
	
	return $text;
}


function addFriend($friend_id) {
    $userid = $_COOKIE['fcUserID'];
    
    //make sure to not add friend twice
    $sql = "SELECT * FROM `friends` WHERE `userid`='$userid' AND `friendid`='$friend_id' ORDER BY `id`";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    $id = $row['id'];
    if($id == "") {
        $addSQL = "INSERT INTO `friends` (`id`, `userid`, `friendid`) VALUES('', '$userid', '$friend_id')";
        return mysql_query($addSQL);
    }

}

function searchFC($keyword) {
    $userid = $_COOKIE['fcUserID'];
    //echo("Call<br />");
    //search through tags
    $sql = "SELECT * FROM `tags` WHERE `tag` LIKE '$keyword'";
    $result = mysql_query($sql);
    //echo("Result: $result<br />");
    
    //put all similar tags fcid into an array
    $fcIDArray = array();
    while($row = mysql_fetch_array($result)) {
        $fcIDArray[] = $row['fc_id'];
    }
    
    $text = "";
    
    //iterate through that array and display flashcards onto screen
    for($i = 0; $i < count($fcIDArray); $i++) {
    //echo("$userid<br />");
	   $fcSQL = "SELECT * FROM `flashcard` WHERE `flashcard_id`='$fcIDArray[$i]' AND `user_id`='$userid'";
	   $fcResult = mysql_query($fcSQL) or die(mysql_error());
	   //echo("<br />For loop");
	   if($fcResult) {
		 //echo("<br />if statement");
		  $x = 1;
		  //echo("<br />before the while loop: $fcResult");
		  while($row = mysql_fetch_array($fcResult)) {
		      //echo("<br />While loop");
		      if($x == 6) {
                $text .= "</tr><tr>";
                $x = 1;
              }
			//$text .= $row['user_name'] . "<br />";
			$text .= "<td><center><div class=\"fcbox\" onclick=\"location.href='view.php?fcid=" . $row['flashcard_id'] ."';\"><a class=\"fclink\" href=\"edit.php?fcid=" . $row['flashcard_id'] ."\">" . nl2br(mimetex($row['front'])) . "</a></div></td>";
			$x++;
		}
		//$text= "Results";
	} else {
		$text = "No results";
	}
  }
	return $text;
}

function searchPFC($keyword) {
    $userid = $_COOKIE['fcUserID'];
    //echo("Call<br />");
    //search through tags
    $sql = "SELECT * FROM `tags` WHERE `tag` LIKE '$keyword'";
    $result = mysql_query($sql);
    //echo("Result: $result<br />");
    
    //put all similar tags fcid into an array
    $fcIDArray = array();
    while($row = mysql_fetch_array($result)) {
        $fcIDArray[] = $row['fc_id'];
    }
    
    $text = "";
    
    //iterate through that array and display flashcards onto screen
    for($i = 0; $i < count($fcIDArray); $i++) { 
	   $fcSQL = "SELECT * FROM `flashcard` WHERE `flashcard_id`='$fcIDArray[$i]' AND `user_id`!='$userid'";
	   $fcResult = mysql_query($fcSQL) or die(mysql_error());
	   if($fcResult) {
		  $x = 1;
		  //echo("<br />before the while loop: $fcResult");
		  while($row = mysql_fetch_array($fcResult)) {
		      //echo("<br />While loop");
		      if($x == 6) {
                $text .= "</tr><tr>";
                $x = 1;
              }
            if(checkPublic($row['group_id'])) {  
                $text .= "<td><center><div class=\"fcbox\" onclick=\"location.href='view.php?fcid=" . $row['flashcard_id'] ."';\"><a class=\"fclink\" href=\"edit.php?fcid=" . $row['flashcard_id'] ."\">" . nl2br(mimetex($row['front'])) . "</a></div></td>";
			    $x++;
            }
		}
		//$text= "Results";
	} else {
		$text = "No results";
	}
  }
	return $text;
}

function checkPublic($groupID) {
    $sql = "SELECT * FROM `group` WHERE `group_id`='$groupID' ORDER BY `group_id` DESC LIMIT 1";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    $public = $row['public'];
    if($public == '1')
        return true;
    else
        return false; 
}


function importGroup($groupID) {
    //get userid
    $userid = $_COOKIE['fcUserID'];
    
    //get group name
    $sql = "SELECT * FROM `group` WHERE `group_id`='$groupID' ORDER BY `group_id`";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    $groupName = $row['name'];
    $public = $row['public'];
    
    $rtnval = true;
    
    if($public == '1') {
        //create group and get new group id
        $newGroupID = createGroup($userid, $groupName);
        if($newGroupID != "") {    
            //import
            $fcsql = "SELECT * FROM `flashcard` WHERE `group_id`='$groupID' ORDER BY `flashcard_id` ASC";
            $importResult = mysql_query($fcsql) or die(mysql_error());
            while($row = mysql_fetch_array($importResult)) {
                $front = $row['front'];
                $back = $row['back'];
                //echo("front: ".$row['front']."back: $back GroupID: $groupID<br />");
                if(!CreateFC($userid, $front, $back, $newGroupID))
                    $rtnval = false;
            }
        }
    }
    
    return $rtnval;
}

function createGroup($userid, $groupName) {
    //insert into database
    $sql = "INSERT INTO `group` (`group_id`, `user_id`, `name`, `public`) VALUES('', '$userid', '$groupName', '1')";
    mysql_query($sql);
    
    //and get the new groupid
    $sql = "SELECT * FROM `group` WHERE `user_id`='$userid' ORDER BY `group_id` DESC LIMIT 1";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    
    //return it
    return $row['group_id'];
}

function checkEmpty($groupid) {
    $sql = "SELECT * FROM `flashcard` WHERE `group_id`='$groupid` AND (`flashcard != "" OR `flashcard` IS NOT NULL)";
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

function getName($userid) {
    $sql = "SELECT * FROM `user` WHERE `user_id`='$userid'";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    
    return $row['user_name'];
} //returns username of the passed in userid

?>