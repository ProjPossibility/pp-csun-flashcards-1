<?php

if (isset($_GET['url'])) {
  header('Location: ' . $_GET['url']);
}

echo "Redirecting.";

?>