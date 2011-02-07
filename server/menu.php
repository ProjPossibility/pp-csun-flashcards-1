<div style="text-align: center;"><a href="index.php">Home</a> | <?php
if ($user_info) {
  echo "<a href=\"browse.php\">Browse</a> | <a href=\"edit.php\">Create Flashcard</a> | <a href=\"create_group.php\">Create Group</a> | <a href=\"logout.php\">Log Out</a>";
} else {
  echo "<a href=\"login.php\">Log In</a> | <a href=\"register.php\">Register</a>";
}
?></div>