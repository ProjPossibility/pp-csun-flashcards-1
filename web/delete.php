<?php

include('mysql.php');
include('cookieChecker.php');
include('functions.php');
include('latex_convert.php');
include('parse_math.php');
include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');

ForceLogin();

if ($_GET['fcid'])
  $fcid = intval($_GET['fcid']);
else
  die("No flashcard ID.");

$sql = 'SELECT user_id, front FROM flashcard WHERE flashcard_id = ' . $fcid;
$result = mysql_query($sql) or die(mysql_error());

if (!$result)
  die("No flashcard was found.");

$fc_info = mysql_fetch_row($result);

if ($fc_info[0] != $user_info['user_id'])
  die("You don't own that flashcard.");

if ($_POST['confirm']) {
  DeleteFC($fcid);
  echo "You deleted your flashcard. Go back to the <a href=\"user-index.php\">user index</a>.";
} else {
?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>SS12 | Confirm Deletion of Flashcard</title>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<style type="text/css">
    A:link {text-decoration: underline; color: #fff;}
    A:visited {text-decoration: underline; color: #fff;}
    A:active {text-decoration: underline; color: #fff;}
    A:hover {text-decoration: underline; color: #fff;}

    body {
        background: #2e4255;
    }
    div#navigation {
        margin: 0 auto;
        color: #FFF; 
        font-size: 16px;
        font-family: verdana;
    }
    
    div.navlinks {
        background: #304b66;
        border: 3px solid #304b66;
        border-radius: 5px;
        -moz-border-radius: 5px;
        text-align: center; 
        margin: 0 auto;
        color: #FFF; 
        font-size: 16px;
        font-family: verdana;
        margin-right: 10px;
        cursor: pointer;    
    }
    div#flashcards {
        background: #304b66;
        border: 2px solid #304b66;
        border-radius: 15px;
        width: 625px;
        -moz-border-radius: 15px;
        padding: 10px;
        margin: 0 auto;
    }

    div.fcbox {
        width: 600px;
        background: #FFF;
        height: 300px;
        line-height: 140px;
        border: 1px solid #fff;
        border-radius: 15px;
        -moz-border-radius: 15px;
        text-align: center;
        margin: 0 auto;
    }
    
    div#buttons {
        margin: 0 auto;
    }

    div.form {
        text-align: center;
        padding-top: 10px;
    }
    
    .stretch {
        width:100%;
        height:100%;
    }
    .text p{
        vertical-align: 50%;
    }
    
    textarea.input {
        border: 1px solid #fff;
        border-radius: 5px;
    }
    
    table#form {
        color: #fff;
        font-family: verdana;
        font-size: 14px;
    }
    li {
        float: right;
        list-style-type: none;
    }
    p.confirm {
      color: #fff;
      text-align: center;
    }
</style>
</head>
<body>
    <div id="container">
        <div id="navigation">
            <ul>
                <li><div class="navlinks"><a href="logout.php">Log Out</a></div></li>
                 <li><div class="navlinks"><a href="app.apk">Download The 
					 Android App </a></div></li>
                <li><div class="navlinks"><a href="edit.php">Create Flashcard</a></div></li>
                <li><div class="navlinks"><a href="user-index.php">Home</a></div></li>
            </ul>
        </div>
        <br /><br />
        <div id="flashcards">
        <p class="confirm">Are you sure you want to delete this?</p>
        <div class="fcbox"><?php echo mimetex($fc_info[1]); ?></div>
        <div class="form">
        <form action="delete.php?fcid=<?php echo $fcid; ?>" method="post">
        <input type="submit" name="confirm" value="Confirm" />
        </form>
        </div>
        </div>
    </div>
</body>
</html><?php
}
?>
  