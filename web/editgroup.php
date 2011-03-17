<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php
include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');
include('../latex_convert.php');
include('../parse_math.php');
include('/usr/home/varcvic/domains/calqlus.org/public_html/mimetex/mimetex.php');

ForceLogin();

$getGroupID = $_GET['id'];

if($getGroupID != -1) {
	$sql = "SELECT * FROM `group` WHERE `group_id`='$getGroupID'";
	$result = mysql_query($sql);
	while($row = mysql_fetch_array($result)) {
		$groupName = $row['name'];
		$public = $row['public'];
	}
}
?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<title>SS12 | Manage Groups</title>
<style type="text/css">
    body {
        background: #45607a;
        margin: 0 auto;
    }
    
    #header {
        background: #2e4255;
        width: 100%;
        height: 35px;
        border-bottom: 1px solid #000;
    }
    
    table#header {
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
        width: 50%;
        background: url('images/group_bg.jpg');
        height: 35px;
        line-height: 35px;
        background-repeat: repeat-x;
    }
    
    div.groupfc {
		float: middle;
        margin: 0 auto;
        width: 50%;
        background: #344c66;
        border-bottom-left: 2px solid #304b66;
        border-bottom-left-radius: 5px;
        border-bottom-right: 2px solid #304b66;
        border-bottom-right-radius: 5px;
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
</style>
</head>
<body>
    <div id="header">
        <table id="header">
            <tr>
                <td style="width: 70%">
                    <img src="images/header.jpg" title="SS12 Flashcards Logo" alt="SS12 Flashcards logo" />
                </td>
                <td>
                    <center><a href="user-index.php">Home</a></center>
                </td>
                <td>
                    <center><a href="edit.php">Create Flashcard</center>
                </td>
                <td>
                    <center><a href="groups.php">Manage Groups</center>
                </td>
                <td>
                    <center><a href="logout.php">Log Out</center>
                </td>
            </tr>
        </table>
    </div>
	<br/ >
	<div id="content">
		<div class="groups">
			<?php echo("<b>$groupName</b>"); ?>
		</div>
		<div class="groupfc">
			<center>
			<form action="modifygroup.php" method="post">
			<font style="font-family: lucida grande; font-size: 18px; color: #FFFFFF;">Group Name<br />
			<?php echo("<input type=\"text\" name=\"groupName\" id=\"groupName\" style=\"font-size: 16px; height: 30px; border: 2px solid #ECECEC; border-radius: 5px; -moz-border-radius: 5px;\" value=\"$groupName\" />"); ?>
			<br />
			Public:
			<br />
			<?php if($getGroupID == -1)
						echo("<input type=\"hidden\" name=\"add\" id=\"add\" value=\"1\" />");
			?>
			<select name="public" id="public">
				<?php 
					if($public != "") {
						if($public == 1)
							echo("<label for=\"1\">Yes</label><option value=\"1\" selected=\"selected\">Yes</option>");
						else
							echo("<label for=\"0\">No</label><option value=\"0\" selected=\"selected\">No</option>");
					}
				?>
				<label for="1">Yes</label><option value="1">Yes</option>
				<label for="0">No</label><option value="0">No</option>
			</select>
			<?php echo("<input type=\"hidden\" value=\"$getGroupID\" name=\"groupID\" id=\"groupID\" />"); ?>
			<br />
			<table>
				<tr>
					<td>
						<input type="submit" value="Submit" />
					</td>
					<td>
						<input type="reset" value="Reset" />
						</form>
					</td>
					<td>
						<form action="modifygroup.php?id=-1" method="post">
						<?php echo("<input type=\"hidden\" value=\"$getGroupID\" name=\"groupID\" id=\"groupID\" />"); ?>
						<input type="submit" value="Delete" />
						</form>
					</td>
				</tr>
			</table>
			<br />
		</div>
	</div>
</div>
</body>
</html>