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
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<title>SS12 | Manage Groups</title>
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
		<br />
		<div class="groups">
			<img src="images/arrow.gif" alt="arrow" /><a name="manage"><b>Manage Groups</b></a>
		</div>
		<div class="groupfc">
			<a href="editgroup.php?id=-1" style="float: right; padding-right: 5px;">Add New Group</a>
			<br />
			<font style="color: #fff; font-family: lucida-grande; padding: 5px;"><b>Please select a group to modify:</b></font>
			<ul class="updates">
			<?php
				for($i = 0; $i < count($groupArray); $i++) {
					echo("<li><a href=\"editgroup.php?id=$groupArray[$i]\">$groupNames[$i]</a></li>");
				}
			?>
			</ul>
			<br />
		</div>
	</div>
</body>
</html>
	