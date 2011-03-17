<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php

//declare GETS
$fcID = $_GET['fcid'];
$front = $_GET['front'];
$group = $_GET['group'];


include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');
include('../latex_convert.php');
include('../parse_math.php');
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
<script src="../jquery/jquery-1.5.1.js" type="text/javascript"></script>
<style type="text/css">
    A:link {text-decoration: underline; color: #fff;}
    A:visited {text-decoration: underline; color: #fff;}
    A:active {text-decoration: underline; color: #fff;}
    A:hover {text-decoration: underline; color: #fff;}

    A.fclink:link {text-decoration: none; color: #000;}
    A.fclink:visited {text-decoration: none; color: #000;}
    A.fclink:active {text-decoration: none; color: #000;}
    A.fclink:hover {text-decoration: underline; color: #000;}

    p.hilite { background:yellow; }
    
    p { color:red; margin:5px; cursor:pointer; }

    body {
        background: #45607a;
        margin: 0 auto;
    }
    
    #headerTop {
        background: #2e4255;
        width: 100%;
        height: 35px;
        border-bottom: 1px solid #000;
    }
    
    table#headerTopTable {
         font-family: "lucida grande",tahoma,verdana,arial,sans-serif; 
         font-size: 14px; 
         color: #FFF;
         width: 100%;
    }
    
    div#leftcol {
        float: left;
        background: #304b66;
        margin-top: 4px;
        width: 180px;
        font-family: "lucida grande",tahoma,verdana,arial,sans-serif; 
        font-size: 12px; 
        color: #FFF;
    }
    
    ul.updates {
        list-style-image: url('images/bullet.jpg');
        list-style-position: outside;
        padding-left: 15px;
        margin: 5px;
    }
    
    div#content {
        width: 100%;
        margin-top: 4px;
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
<body>
   <div id="headerTop">
        <table id="headerTopTable" border="0">
            <tr>
                <td style="width: 25%;">
                    <img src="images/header.jpg" title="SS12 Flashcards Logo" alt="SS12 Flashcards logo" />
                </td>
				<td style="width: 35%;">
					<form action="search.php" method="post">
					<input type="text" style="border: 1px solid #FFF; border-radius: 5px; font-size: 14px; height: 25px; width: 300px;" value="Search" name="keyword" id="keyword" />
					<input type="submit" value="Search" />
					</form>
				</td>
                <td>
                    <center><a href="user-index.php">Home</a></center>
                </td>
                <td>
                    <center><?php echo("<a href=\"#$groupNames[0]\">Skip Navigation</a>"); ?></center>
                </td>
                <td>
                    <center><a href="edit.php">Create Flashcard</a></center>
                </td>
                <td>
                    <center><a href="groups.php">Manage Groups</a></center>
                </td>
                <td>
                    <center><a href="logout.php">Log Out</a></center>
                </td>
            </tr>
        </table>
    </div>
    <div id="content">
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
        <br />
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
                    <td style="width: 25%">
                        <?php 
                            if($prevFCID != "")
                                echo("<a href=\"view.php?fcid=$prevFCID&amp;front=1\"><img src=\"images/prev.jpg\" alt=\"Previous Flashcard\" title=\"Previous Flashcard\" style=\"border: 0px;\" /></a>"); 
                        ?>
                    </td>
                    <td style="text-align: center; width: 25%;">
                        <?php
                            if($front == "0") {
                                $convert = ConvertToTextFull($backText);
                                echo("<div id=\"textConvert\" style=\"display: none;\">$convert</div>");
                                if(strlen($convert) < 100)
                                {
                                	$convert = preg_replace( '/\s+/', ' ', trim( $convert ) );  
                                	echo("<div id=\"jscript\" style=\"display: none;\"><a href=\"#\" id=\"tts\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                                	//onclick=\"DHTMLSound('http://www.hackroute.com/index.php?tts=$convert');\"
                               		echo("<div id=\"noscript\"><a href=\"redirect.php?url=http://www.hackroute.com/index.php?tts=$convert\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                            	}
                            } else {
                            
                                $convert = ConvertToTextFull($frontText);
                                 echo("<div id=\"textConvert\" style=\"display: none;\">$convert</div>");
                                if(strlen($convert) < 100)
                                {
                                	$convert = preg_replace( '/\s+/', ' ', trim( $convert ) ); 
                                	//echo("<div id=\"jscript\" style=\"display: none;\"><a href=\"#\" onclick=\"DHTMLSound('http://translate.google.com/translate_tts?tl=en&amp;q=$convert');\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                                	 echo("<div id=\"jscript\" style=\"display: none;\"><a href=\"#\" id=\"tts\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                                    //onclick=\"DHTMLSound('http://hackroute.com/index.php?tts=$convert');\"
                                	echo("<div id=\"noscript\"><a href=\"redirect.php?url=$convert\"><img src=\"images/speak.jpg\" style=\"border: 0px\" alt=\"Press to hear flash card\" title=\"Press to hear flash card\" /></a></div>");
                            	}
                            }
                        ?>
                    </td>
                    <td style="text-align: right; width: 25%;">
                        <?php echo("<a href=\"http://hackroute.com/redirect.php?tts=$convert\" target=\"_blank\"><img src=\"images/mp3.jpg\" alt=\"Press hear to download sound file\" title=\"Press hear to download sound file\" style=\"border: 0px\" /></a>"); ?>
                    </td>
                    <td style="text-align: right; width: 25%;">
                        <?php
                            if($nextFCID != "") 
                                echo("<a href=\"view.php?fcid=$nextFCID&amp;front=1\"><img src=\"images/next.jpg\" alt=\"Next Flashcard\" title=\"Next Flashcard\"  style=\"border: 0px;\" /></a>"); 
                        ?>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="dummyspan"></div>
</body>
<script type="text/javascript">
    $(document).ready(function() {
        document.getElementById('jscript').style.display='block';
        document.getElementById('noscript').style.display='none';
    });
    
    $("#tts").click(function(){
        $.getJSON("http://www.hackroute.com/index.php",
        {'tts' : $("#textConvert").html()},
        function(data) {
            //alert(data.file);
            $("#dummyspan").html("<embed src=\""+ data.file +"\" autostart=\"true\" hidden=\"true\" />");
        });
    });
    
    /*
    $("p").click(function() {
        alert('Handler for .click() called.');
    });
    
    $("p").hover(function () {
      $(this).addClass("hilite");
    }, function () {
      $(this).removeClass("hilite");
    });
    */
</script>
</html>