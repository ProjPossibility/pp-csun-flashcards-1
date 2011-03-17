<?php

include('../mysql.php');
include('../cookieChecker.php');
include('../functions.php');

if(importGroup(6))
    echo("Import successful");
else
    echo("Nope");

?>