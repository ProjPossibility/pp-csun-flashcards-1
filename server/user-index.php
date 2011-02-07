<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php
include('mysql.php');
include('cookieChecker.php');
include('functions.php');
include('latex_convert.php');
include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');

ForceLogin();

$sql = "SELECT * FROM `group` WHERE `user_id`='$userid' ORDER BY group_ID ASC";
$result = mysql_query($sql);
$groupArray = array();
$groupNames = array();
while($row = mysql_fetch_array($result)) {
    $groupArray[] = $row['group_id'];
    $groupNames[] = $row['name'];
}
?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>SS12 | Flashcards</title>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
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
        
    div.flashcards {
        background: #304b66;
        border: 2px solid #304b66;
        border-radius: 15px;
        -moz-border-radius: 15px;
        padding: 10px;
        color: #FFF; 
        font-size: 16px;
        font-family: verdana; 
    }
    div.fcbox {
        background: url('images/fcbg.jpg');
        background-repeat: repeat;
        width: 200px;
        line-height: 20px;
        height: 140px;
        border: 1px solid #fff;
        border-radius: 15px;
        -moz-border-radius: 15px;
        text-align: center;
        cursor: pointer;
        font-size: 12px;
    }
    .text p{
        vertical-align: 50%;
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
<body>
    <div id="navigation">
        <ul>
            <li><div class="navlinks"><a href="logout.php">Log Out</a></div></li>
            <li><div class="navlinks"><a href="edit.php">Create Flashcard</a></div></li>
            <li><div class="navlinks"><a href="user-index.php">Home</a></div></li>
        </ul>
    </div>
    <img src="images/flashcard.jpg" alt="Flashcards" />
        <?php
        for($i = 0; $i < count($groupArray); $i++) {
            $sql = "SELECT * FROM `flashcard` WHERE `user_id`='$userid' AND `group_id`='$groupArray[$i]'";
            $result = mysql_query($sql) or die(mysql_error());
            if($groupArray[$i] != 0) {
            echo("<div class=\"flashcards\">");
            echo("$groupNames[$i]<br /><table style=\"width: 100%\"><tr>\n");
            $x = 1;
            while($row = mysql_fetch_array($result)) {
                if($x == 6) {
                    echo("</tr>\n<tr>\n");
                    $x = 1;
                }
                echo("<td><center><div class=\"fcbox\" onclick=\"location.href='edit.php?fcid=" . $row['flashcard_id'] ."';\" title=\"Edit this flashcard\"><a class=\"fclink\" href=\"edit.php?fcid=" . $row['flashcard_id'] ."\">" . mimetex($row['front']) . "</a></div><br /><a href=\"delete.php?fcid=" . $row['flashcard_id'] . "\"><img src=\"images/del.jpg\" alt=\"Delete this flashcard\" title=\"Delete this flashcard\" style=\"border: 0px;\" /></a></center></td>\n");
                $x++;
            }
            }
            echo("<td rowspan=\"2\">
                    <a href=\"view.php?group=$groupArray[$i]\"><img src=\"images/play.jpg\" alt=\"View this groups flashcards\" title=\"View this groups flashcards\" style=\"border: 0px;\" /></a>
                    <br /><br />
                    <a href=\"edit.php?group=$groupArray[$i]\"><img src=\"images/plus.jpg\" alt=\"Add more flashcards\" title=\"Add more flashcards to this group\" style=\"border: 0px;\" /></a>
                </td>
            </tr>
        </table></div><br />");
        }
        ?>
</body>
</html>    