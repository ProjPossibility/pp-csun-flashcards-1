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
$groupPublic = array();
while($row = mysql_fetch_array($result)) {
    $groupArray[] = $row['group_id'];
    $groupNames[] = $row['name'];
	$groupPublic[] = $row['public'];
}
?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<title>SS12 | Flashcards</title>
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
    
    h2 {
        font-family: lucida grande;
        color: #000;
        font-size: 14px;
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
    <div id="leftcol">
            <img src="images/netUpdates.jpg" alt="Network Updates" />
            <ul class="updates">
                <?php
                    $sqlFriends = "SELECT * from `friends` WHERE `userid`='$userid' ORDER BY `id` ASC";
                    $result = mysql_query($sqlFriends) or die(mysql_error());
                    while($row = mysql_fetch_array($result)) {
                        $friend_id = $row['friendid'];
                        $username = getName($row['friendid']);
                        $sqlIndivFrnd = "SELECT * FROM `flashcard` WHERE `user_id`='$friend_id' ORDER BY `flashcard_id` ASC LIMIT 3";
                        $res = mysql_query($sqlIndivFrnd);
                        while($row2 = mysql_fetch_array($res)) {
                            if(checkPublic($row2['group_id']))
                                echo("<li><a href=\"profile.php?id=$friend_id\"><b>$username</b> New Flascard: " . $row2['front'] . "</a></li>");
                        }
                    }
                ?>
            </ul>
            <br />
    </div>
    <br />
    <div id="content">
        <?php
        for($i = 0; $i < count($groupArray); $i++) {
            $sql = "SELECT * FROM `flashcard` WHERE `user_id`='$userid' AND `group_id`='$groupArray[$i]'";
            $result = mysql_query($sql) or die(mysql_error());
            if($groupArray[$i] != 0 && checkGroup($groupArray[$i])) {
                $anchor = str_replace(' ', '', $groupNames[$i]);
                echo("<div class=\"groups\"><img src=\"images/arrow.gif\" alt=\"Arrow Pointer\" /><a name=\"$anchor\">$groupNames[$i]</a></div>");
                echo("<div class=\"groupfc\"><table style=\"width: 100%;\"><tr>");
                $x = 1;
                while($row = mysql_fetch_array($result)) {
                    if($x == 6) {
                        echo("</tr><tr>");
                        $x = 1;
                    }
                    echo("<td><center><div class=\"fcbox\" onclick=\"location.href='edit.php?fcid=" . $row['flashcard_id'] ."';\"><a class=\"fclink\" href=\"edit.php?fcid=" . $row['flashcard_id'] ."\">" . nl2br(mimetex($row['front'])) . "</a></div>");
                    echo("<a href=\"delete.php?fcid=" . $row['flashcard_id'] . "\"><img src=\"images/del.jpg\" alt=\"Delete this flashcard\" style=\"border: 0px;\" /></a></center></td>");
                $x++;
                }
            echo("</tr></table><br />");
            echo("<table style=\"width: 100%\" border=\"0\"><tr><td style=\"text-align: right; width: 90%;\"><a href=\"view.php?group=$groupArray[$i]\"><img src=\"images/play.jpg\" alt=\"View this groups flashcards\" style=\"border: 0px;\" /></a></td><td style=\"text-align: right;\"><a href=\"edit.php?group=$groupArray[$i]\"><img src=\"images/add.jpg\" alt=\"Add more flashcards\" style=\"border: 0px;\" /></a></td><td style=\"text-align: right;\">");
			if($groupPublic[$i] == '0')
				echo("<a href=\"public.php?id=1&amp;groupID=$groupArray[$i]\"><img src=\"images/unlock.jpg\" alt=\"Unlock Group\" style=\"border: 0px;\" /></a>");
			else
				echo("<a href=\"public.php?id=0&amp;groupID=$groupArray[$i]\"><img src=\"images/private.jpg\" alt=\"Lock Group\" style=\"border: 0px;\" /></a>");
			echo("</td><td style=\"text-align: right;\"><a href=\"http://twitter.com/share?url=http://calqlus.org/ss12/view.php?group=$groupArray[$i]&amp;text=Studying my $groupNames[$i] flashcards using %23SS12 %23flashcards&amp;via=SS12Flashcards\"><img src=\"images/twitter.jpg\" style=\"border: 0px;\" alt=\"Post to twitter\" /></a></td></tr></table></div><br />");
        }
        }
        ?>
        </div>
</body>
</html>