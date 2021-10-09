# Fiek Trivia App

This application is created and developed by Nora Berisha and Rinor Ahmeti.

### About 

This application is written in Java. It consists of multiple views, activities and fragments.

In this app we have used Firebase for authentication and storing user information such as high score (as an external database). 
Apart from Firebase, we have used SQLite as an internal database in order to save the quizzes created by the user and the scores.

The user has the option to play quizzes by selecting the category, number of questions and difficulty.
These questions are fetched from [Open Trivia DB](https://opentdb.com/) which is an external API.

The results of the quizzes are stored on SQLite (local database) and the user has the option to delete them.
The user high score is stored on Firebase and the top five high schores are shown on the leaderboard view.

Along with this, the user has the option to create a custom quiz and play it. The custom quiz is saved locally on SQLite database and can be deleted.



