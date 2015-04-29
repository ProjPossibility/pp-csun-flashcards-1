# Introduction #
The SS12 Flashcard website started as a task given to our group team during the SS12 Competition at California State University, Northridge. Since placing in first, we've been constantly updating and working on our project to move it to the next level. Using innovation and creativity we've tried to transform this simple idea into a standard of studying and sharing information.

# Pages Overview #
  * Login
  * Register
  * User-Index
  * Profile
  * Create/Edit Flashcards
  * Manage Groups
  * View Flashcards

# Page Explanation #
# Login #
The login page serves a very simple purpose, just as its name describes the page provides two form fields, username and password, and allows users to login. If users do not have an account they can register and begin creating flashcards and studying from there.
# Register #
The register page which is accessed from the login page, provides two simple fields and allows users to register to the website by providing a username and password.
# User-Index #
The User-Index page is the first page the user sees when logging in. The user is now presented with the main navigation of the website. Using the navigation bar the user has the ability to:
**Search for users or through flashcards**Skip Navigation (accessibility feature for screen readers, this way users using screen readers will be able to skip hearing the whole navigation every time they access a page)
  * Create Flashcards
  * Manage Groups
  * Log Out
In the User-Index the user is present with several features and options. The left side of the page includes the Network Updates section which will show all public updates that the user's friends make. The main portion of the page is where the user is presented with the flashcards they've created, which are broken down into groups, and flashcard and group functionality such as the ability to study a group (play-like button), add flashcards to this specific group, lock or unlock the group, and share the selected group with twitter. The flashcards are presented in rows of five and clicking an individual flashcard will allow you to edit that specific flashcard. The flashcards also have a delete button (marked with an "X" inside of them), that will allow you to delete the flashcard that button is located under.
# Profile #
The Profile page is very similar to the User-Index page, except for the limited functionality, due to the fact that the flashcards that a user is viewing is simply not theirs. Although flashcards may belong to a public group, we do not want other user's modifying flashcards that are not theirs. To combate this limited access, we've added the ability to import flashcards from a public group, so that users will not have to access a specific user's page just to study flashcards and have the ability to edit flashcards how he or she may want. The profile page offers users the ability to add the person the profile they're viewing as a friend, study another user's public flashcards, tweet that they are studying this user's group of flashcards, and view an individual flashcard simply by clicking that flashcard.
# Create/Edit Flashcards #
The create and edit flashcard page is one page using a simple get statement. Since the forms are identical, we've decided to have a single page serve multiple purposes, in this case both edit and create. The page is populated with textareas so that user's can provide a front and back to a flashcard and select a group that they would like to add them too. If a user has not created any groups, they can select "New Group" from the drop down menu and will be presented with several options as the group name and if they want the group to be public. Instructions are provided to the right of the field of the syntax used for creating mathematical symbols using Mimetex.

The page determines if it is creating or editing a flashcard by the information passed to the page. If for example the page is passed with the conditions "?fcid=231", the information from flashcard 231 in the database will be populated in the fields, only if the user owns this flashcard. After a user submits the information from the form, it is handled by a create or edit function which have almost identical functionality. Both functions parse the information passed in and either update or create new tags and a new flashcard. The tags that are created are used for easing the process of searching for flashcards. The functions parsing the strings will ignore typical words that would appear in a question such as "what", "is", "the", etc. After tags are created or updated, a flashcard is either created or updated and the create and edit functions return successfully.
# Manage Groups #
The Manage Groups page provides the user with an easy way to add, change, and delete groups and their entire content. The user is presented with a bulleted list of the groups they have previously created and a link to create a new group. This page carries the user over into the a page that allows the user to make the actual changes. Again, this group serves dual purposes similar to the create/edit flashcard page. If conditions are provided, for example "id?=62", the form fields will be populated with the information of a group with the group id 62. The form fields that are provided are Group Name, Public (drop down list with two fields, yes or no), and three buttons, submit, reset, and delete. Submitting a button will create or update a group, the reset button will reset the contents of the forms, and delete will delete the group and all of the flashcards in that group.
# View Flashcards #
The View Flashcards page can be thought of as the study mode page. Here the user is provided with a large flash card that shows the front of the flashcard. If the user clicks on the flash, the page will refresh, and flip the flashcard to show the back of the card. The user is also provided with certain key options here. The ability to move to the next or previous flash card and the ability to either hear the flash card repeated back to them or download an audible wav file of a text-to-speech voice repeating the string on the card file.