<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php

//check passed in to create or edit
$fcID = $_GET['fcid'];

include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');

ForceLogin();
?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<title>SS12 | Flashcards</title>
<script type="text/javascript">
    function hide() {
        var g = document.getElementById('groups1');
        g.style.display = "none";
        g = document.getElementById('groups2');
        g.style.display = "none";
    }
    
    function show(x) {
        if(x == "new") {
            var g = document.getElementById('groups1');
            g.style.display = "block";
            g = document.getElementById('groups2');
            g.style.display = "block";
        } 
    }
</script>
<style type="text/css">
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
    
    div.groups {
        float: middle;
        margin: 0 auto;
        width: 70%;
        background: url('images/group_bg.jpg');
        height: 35px;
        line-height: 35px;
        background-repeat: repeat-x;
    }
    
    div.groupfc {
        float: middle;
        margin: 0 auto;
        width: 70%;
        background: #344c66;
        border-bottom-left: 2px solid #304b66;
        border-bottom-left-radius: 5px;
        border-bottom-right: 2px solid #304b66;
        border-bottom-right-radius: 5px;
        border-top-left: 2px solid #304b66;
        border-top-left-radius: 5px;
        border-top-right: 2px solid #304b66;
        border-top-right-radius: 5px;
        -moz-border-radius: 5px;
    }
    
    div.fcbox {
        background: url('images/fcbg.jpg');
        margin: 0 auto;
        background-repeat: repeat;
        width: 150px;
        line-height: 20px;
        height: 100px;
        border: 1px solid #fff;
        border-radius: 15px;
        -moz-border-radius: 15px;
        text-align: center;
        cursor: pointer;
        font-size: 12px;
    }
    A.fclink:link {text-decoration: none; color: #000;}
    A.fclink:visited {text-decoration: none; color: #000;}
    A.fclink:active {text-decoration: none; color: #000;}
    A.fclink:hover {text-decoration: underline; color: #000;}
    
    A:link {text-decoration: underline; color: #fff;}
    A:visited {text-decoration: underline; color: #fff;}
    A:active {text-decoration: underline; color: #fff;}
    A:hover {text-decoration: underline; color: #fff;}
    
    table#form {
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
    <div id="headerTop">
        <table id="headerTopTable">
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
    <br />
    <div class="groupfc">
        <center><a name="editor"><img src="images/fc_editor.jpg" alt="Flashcard Editor title image" style="border: 0px;" /></a></center>
        <form action="insert.php" method="post">
        <?php echo("<input type=\"hidden\" name=\"fcID\" value=\"$fcID\" />"); ?>
        <br /><br />
        <table border="1">
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
                            echo("<option value=\"$row[0]\">$row[1]</option>");
                    }
                ?>
                    <option value="new">New Group</option>
                    </select>
                </td>
            </tr>
            <tr id="groups1">
                <td>
                    <label for="newGroupName">Group Name:</label>
                </td>
                <td style="text-align: right; align: right;">
                    <input type="text" name="newGroupName" id="newGroupName" style="border: 1px solid #fff; border-radius: 5px; align: right;" />
                </td>
            </tr>
			<tr id="groups2">
				<td>
					<label for="public">Public Group</label>
				</td>
				<td style="text-align: right;">
					<select name="public" id="public">
						<option value="1">Yes</option>
						<option value="0">No</option>
					</select>
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
         <ul class="updates">
            <li>squareroot( x )</li> 
            <li>power( r, x )</li>
            <li>fraction( r, x )</li>
            <li>+</li>
            <li>-</li>
            <li>*</li>
        </ul>
        Example: [math] squareroot(25) + squareroot(25) = 10 [/math]
        </td>
        </tr>
        </table>
    </div>
    </div>
</body>
</html>