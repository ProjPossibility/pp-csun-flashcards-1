<html>
<head>
<title>SS12 | Logout</title>
<style type="text/css">
    body {
        background: #2e4255;
        color: #FFF;
        font-size: 14px;
        font-family: verdana;
    }
	
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
<?php

setcookie("fcUserID", '', 0); 
setcookie("fcPassword", '', 0);

$user_info = null;

echo "You are logged out.";
echo("<meta http-equiv=\"refresh\" content=\"2;url=index.php\">");

?>
</body>
</html>