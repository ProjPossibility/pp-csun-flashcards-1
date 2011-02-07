<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php
include('mysql.php');
include('cookieChecker.php');

if ($_POST['login']) {
  //identify passed in variables
  $username = mysql_real_escape_string($_POST['username']);
  $password = md5($_POST['password']);

  $link = mysql_connect('localhost', $mysql_user, $mysql_pass) or die('Failure to cnnect to the database ' . mysql_error());
  mysql_select_db("SS12", $link) or die("Could not connect: " . mysql_error());

  $query = "SELECT * FROM `user` WHERE `user_name`='$username' LIMIT 1";

  $result = mysql_query($query);

  $row = mysql_fetch_array($result);

  //determine if the user's login was successful
  if($username == $row['user_name'] && $password = $row['password']) {
      $expire = time() + 60 * 60 * 24 * 30;
      //userid cookie
      setcookie("fcUserID", $row['user_id'], $expire); 
      setcookie("fcPassword", $row['password'], $expire);
      $user_info = $row;
      echo("Logging In...");
      echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index.php\">");
  } else {
      include('menu.php');
      echo("Login unsuccessful");
  }

  mysql_close($link);
} else {
  if ($user_info) {
    echo("<body><font style=\"color: #fff\">Logging in</font></body>");
    echo("<meta http-equiv=\"refresh\" content=\"2;url=user-index.php\">");  
  } else {
?>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8" />
<title>SS12 | Flashcards</title>
<style type="text/css">
    body {
        background: #2e4255;
    }
    
    A:link {text-decoration: underline; color: #fff;}
    A:visited {text-decoration: underline; color: #fff;}
    A:active {text-decoration: underline; color: #fff;}
    A:hover {text-decoration: underline; color: #fff;}
    
	#myoutercontainer { position:relative }
	#myinnercontainer { position:absolute; top:50%; height:10em; margin-top:-5em }
	
    div#flashcards {
        background: #304b66;
        border: 2px solid #304b66;
        border-radius: 15px;
        height: 190px;
        width: 400px;
        padding: 10px;
        text-align: center;
        color: #FFF;
        font-size: 14px;
        font-family: verdana;
    }
</style>
</head>
<body>
    <center>
    <img src="images/ss12.jpg" alt="SS12 - Flashcards" /><br />
    <div id="flashcards">
        <center>
        <form name="input" action="index.php" method="post">
        <table style="height: 65%;">
            <tr>
                <td>
                    <label for="username" style="display: none;">username</label>
                    <input type="text" id="username" name="username" style="border: 1px solid #fff; border-radius: 5px; height: 30px; font-size: 20px;" value="Username" />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="password" style="display: none;">password</label>
                    <input type="password" name="password" id="password" style="border: 1px solid #fff; border-radius: 5px; height: 30px; font-size: 20px;" value="Password" />
                </td>
            </tr>
            <tr>
                <td style="text-align: center;">
                    <input type="submit" name="login" value="Log In" />
                </td>
            </tr>
        </table>
        </form>
        </center>
        <a href="register.html">Register</a>
    </div>
    </center>
</body>
</html>
<?php
}
}
?>