<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php

//declare GETS
$fcID = $_GET['fcid'];
$front = $_GET['front'];
$group = $_GET['group'];


include('mysql.php');
include('cookieChecker.php');
include('functions.php');
include('latex_convert.php');
include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');

ForceLogin();

if($group == "") {
    $sql = "SELECT * FROM `flashcard` WHERE `flashcard_id`='$fcID' ORDER BY flashcard_id DESC LIMIT 1";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    $frontText = $row['front'];
    $backText = $row['back'];
    $userid = $row['user_id'];
    $group = $row['group_id'];
} else {
    $sql = "SELECT * FROM `flashcard` WHERE `group_id`='$group' ORDER BY flashcard_id DESC LIMIT 1";
    $result = mysql_query($sql);
    $row = mysql_fetch_array($result);
    $frontText = $row['front'];
    $backText = $row['back'];
    $fcID = $row['flashcard_id'];
    $userid = $row['user_id'];   
}
if ($row['user_id'] != $user_info['user_id'])
    die("You don't own this flashcard.");

?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>SS12 | Flashcard View</title>
<script type="text/javascript">
function playsound() {
  document.getElementById('sound1').Play();
}

function DHTMLSound(surl) {
  document.getElementById("dummyspan").innerHTML=
    "<embed src='"+surl+"' hidden=true autostart=true loop=false>";
}

function check() {
    document.getElementById('jscript').style.display='block';
    document.getElementById('noscript').style.display='none';
}

</script>
<style type="text/css">
    A:link {text-decoration: underline; color: #fff;}
    A:visited {text-decoration: underline; color: #fff;}
    A:active {text-decoration: underline; color: #fff;}
    A:hover {text-decoration: underline; color: #fff;}

    A.fclink:link {text-decoration: none; color: #000;}
    A.fclink:visited {text-decoration: none; color: #000;}
    A.fclink:active {text-decoration: none; color: #000;}
    A.fclink:hover {text-decoration: underline; color: #000;}

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
        cursor: pointer;
        margin: 0 auto;
    }
    
    div#buttons {
        margin: 0 auto;
        width: 625px;
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

    img {
      border: 0;
    }
</style>
</head>
<body onload="check();">
    <div id="container">
        <div id="navigation">
            <ul>
                <li><div class="navlinks"><a href="logout.php">Log Out</a></div></li>
                <li><div class="navlinks"><a href="edit.php">Create Flashcard</a></div></li>
                <li><div class="navlinks"><a href="user-index.php">Home</a></div></li>
            </ul>
        </div>
        <br /><br />
        <div id="flashcards">
        <?php
            if($front == "0") {
                echo("<div class=\"fcbox\" onclick=\"location.href='view.php?fcid=".$fcID."&amp;front=1'\" title=\"Click to flip flashcard\">");
                echo("<a class=\"fclink\" href=\"view.php?fcid=".$fcID."&amp;front=1\">");
                echo mimetex($backText);
                echo("</a>");
            } else {
                echo("<div class=\"fcbox\" onclick=\"location.href='view.php?fcid=".$fcID."&amp;front=0'\" title=\"Click to flip flashcard\">");
                echo("<a class=\"fclink\" href=\"view.php?fcid=".$fcID."&amp;front=0\">");
                echo mimetex($frontText);
                echo("</a>");
            }
            ?>
        </div>
        </div>
        <div id="buttons">
        <?php
            $sqlPrev = "SELECT * FROM `flashcard` WHERE `flashcard_id`<'$fcID' AND `user_id`='$userid' AND `group_id`='$group' ORDER BY flashcard_id DESC LIMIT 1";
            $result = mysql_query($sqlPrev);
            $row = mysql_fetch_array($result);
            $prevFCID = $row['flashcard_id'];
            $sqlFor = "SELECT * FROM `flashcard` WHERE `flashcard_id`>'$fcID' AND `user_id`='$userid' AND `group_id`='$group' ORDER BY flashcard_id ASC LIMIT 1";
            $result = mysql_query($sqlFor);
            $row = mysql_fetch_array($result);
            $nextFCID = $row['flashcard_id'];
        ?>
            <table border="0" style="width: 100%;">
                <tr>
                    <td style="width: 33%">
                        <?php 
                            if($prevFCID != "")
                                echo("<a href=\"view.php?fcid=$prevFCID&amp;front=1\"><img src=\"images/prev.jpg\" alt=\"Previous Flashcard\" title=\"Previous Flashcard\" style=\"border: 0px;\" /></a>"); 
                        ?>
                    </td>
                    <td style="text-align: center; width: 33%;">
                        <?php
                            if($front == "0") {
                                $convert = ConvertToTextFull($backText);
                                if(strlen($convert) < 100)
                                {
                                	$convert = preg_replace( '/\s+/', ' ', trim( $convert ) );  
                                	echo("<div id=\"jscript\" style=\"display: none;\"><a href=\"#\" onclick=\"DHTMLSound('http://translate.google.com/translate_tts?tl=en&amp;q=$convert');\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                               		echo("<div id=\"noscript\"><a href=\"redirect.php?url=$convert\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                            	}
                            } else {
                            
                                $convert = ConvertToTextFull($frontText);
                                if(strlen($convert) < 100)
                                {
                                	$convert = preg_replace( '/\s+/', ' ', trim( $convert ) ); 
                                	echo("<div id=\"jscript\" style=\"display: none;\"><a href=\"#\" onclick=\"DHTMLSound('http://translate.google.com/translate_tts?tl=en&amp;q=$convert');\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                                	echo("<div id=\"noscript\"><a href=\"redirect.php?url=$convert\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                            	}
                            }
                        ?>
                    </td>
                    <td style="text-align: right; width: 33%;">
                        <?php
                            if($nextFCID != "") 
                                echo("<a href=\"view.php?fcid=$nextFCID&amp;front=1\"><img src=\"images/next.jpg\" alt=\"Next Flashcard\" title=\"Next Flashcard\"  style=\"border: 0px;\" /></a>"); 
                        ?>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <span id='dummyspan'></span>
</body>
</html>