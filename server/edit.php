<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php

//check passed in to create or edit
$fcID = $_GET['fcid'];

include('mysql.php');
include('cookieChecker.php');
include('functions.php');

ForceLogin();
?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<title>SS12 | Flashcard Editor</title>
<script type="text/javascript">
    function hide() {
        var g = document.getElementById('groups1');
        g.style.display = "none";
    }
    
    function show(x) {
        if(x == "new") {
            var g = document.getElementById('groups1');
            g.style.display = "block";
        } 
    }
</script>
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
        -moz-border-radius: 15px;
        padding: 10px;
    }
    div.fcbox {
        background: url('images/fcbg.jpg');
        background-repeat: repeat;
        width: 200px;
        height: 140px;
        line-height: 140px;
        border: 1px solid #fff;
        border-radius: 15px;
        -moz-border-radius: 15px;
        text-align: center;
        cursor: pointer;
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
    li.nav {
        float: right;
        list-style-type: none;
    }
    
    li.info {
        color: #fff;
        font-family: verdana;
        font-size: 14px;
    }
    
    ul.info {
        color: #fff;
        font-family: verdana;
        font-size: 14px;    
    }
    
</style>
</head>
<body onload="javascript:hide();">
<?php
$fcFront = "";
$fcBack = "";
$groupId = -1;
if (isset($_GET['group'])) {
  $groupId = $_GET['group'];
}
if($fcID != "") {
    $query = "SELECT * FROM `flashcard` WHERE `flashcard_id`='$fcID' LIMIT 1";
    $result = mysql_query($query);
    $row = mysql_fetch_array($result);
    $fcFront = $row['front'];
    $fcBack = $row['back'];
    $groupId = $row['group_id'];
    if ($row['user_id'] != $user_info['user_id'])
      die("You don't own this flashcard.");
}
?>
    <div id="navigation">
        <ul>
            <li class="nav"><div class="navlinks"><a href="logout.php">Log Out</a></div></li>
            <li class="nav"><div class="navlinks"><a href="edit.php">Create 
				Flashcard</a></div></li>
            <li class="nav"><div class="navlinks"><a href="user-index.php">Home</a></div></li>
        </ul>
    </div>
    <img src="images/fc_editor.jpg" alt="Flashcard Editor title image" />
    <div id="flashcards">
        <form action="insert.php" method="post">
        <?php echo("<input type=\"hidden\" name=\"fcID\" value=\"$fcID\" />"); ?>
        <br><br>
        <table>
        <tr>
        <td>
        <table id="form">
            <tr>
                <td colspan="2">
                    <label for="TextAreaFlashcardFront">Front:</label><br /><textarea class="input" cols="58" id="TextAreaFlashcardFront" name="TextAreaFlashcardFront" rows="10"><?php echo("$fcFront"); ?></textarea>
                </td>
            </tr>
            
                        <tr>
                <td colspan="2">
                    <label for="TextAreaFlashcardBack">Back:</label><br /><textarea class="input" cols="58" id="TextAreaFlashcardBack" name="TextAreaFlashcardBack" rows="10"><?php echo("$fcBack");?></textarea>
                </td>
            </tr>
            <tr>
                <td>
                <label for="group">Group:</label> 
                <?php
                    $sql = "SELECT group_id, name FROM `group` WHERE user_id = $userid";
                    $result = mysql_query($sql) or die(mysql_error());
                    echo("</td><td style=\"text-align: right\"><select name=\"group\" id=\"group\" onchange=\"javascript:show(this.value);\">");
                    echo("<option value=\"\"></option>");
                    while ($row = mysql_fetch_row($result)) {
                        if($groupId == $row[0])
                            echo("<option value=\"$row[0]\" selected=\"selected\">$row[1]</option>");
                        else
                            echo("<option value=\"$row[0]\">$row[1]</option></label>");
                    }
                ?>
                    <label for="new">New Group Option</label><option value="new">New 
				Group</option>
                    </select>
                </td>
            </tr>
            <tr id="groups1">
                <td>
                    <label for="newGroupName">Group Name:</label>
                </td>
                <td style="text-align: right;">
                    <input type="text" name="newGroupName" id="newGroupName" style="border: 1px solid #fff; border-radius: 5px; align: right;" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
        </table>    
        </form>
        </td>
        <td style="color: #fff; font-family: verdana; font-size: 14px;" valign="top">
        <b>Math Equations</b>
        <br />
        <br />
        Math Equations must be tagged with [math] [/math]
        <br />
        <br />
        LaTeX entry is fully supported.
        <br />
        <br />Functions below currently have TTS Compatibility:
        <br />
         <ul class="info">
            <li class="info">squareroot( x )</li> 
            <li class="info">power( r, x )</li>
            <li class="info">fraction( r, x )</li>
            <li class="info">+</li>
            <li class="info">-</li>
            <li class="info">*</li>
        </ul>
        Example: [math] squareroot(25) + squareroot(25) = 10 [/math]
    </div>
</body>
</html>