# Test Plan

**Author**: Team 43

## 1 Testing Strategy

### 1.1 Overall strategy

We are planning to use unit testing, automated UI testing, system testing and regression testing. The developer(s) will be responsible for creating unit test cases. They also have to make sure that the required code coverage is met. The tester(s) will be responsible for system testing the application. There will be test plan walk through with the Analysts and the Project Manager to make sure all the operations and game logic are adequately covered in test cases. The system testing will be repeated by the testers after enhancements as a form of regression testing to make sure all the existing functionalities work as expected.

### 1.2 Test Selection

We are planning to use white box testing for the implementation as all internal logic need to be thoroughly tested.

We are planning to use the these tools for testing:
* JUnit - Unit Tests 
* Manual Testing - System/Regression Tests

### 1.3 Adequacy Criterion

We are planning to use Android Studio's inbuilt "Run ‘Tests in ‘java’’ with Coverage" feature to make sure appropriate code coverage is achieved. The target is to achieve at least 90% of coverage using the JUnit tests for the non-UI focused classes. For the manual testing, the Testers will make sure to have a walk through with the Analysts to make sure all the functionalities are covered in the testing.

### 1.4 Bug Tracking

We will be using the "Issues" feature on the GitHub to track bugs. It will be assigned to the developer working on the corresponding module of the application. The bug once fixed, the root cause analysis will be updated in the issue itself.

We have logged the bugs/enhancements we found out in the [issues](https://github.gatech.edu/gt-omscs-se-2019fall/6300Fall19Team43/issues?utf8=%E2%9C%93&q=is%3Aissue) section 

### 1.5 Technology

We intend to cover our unit testing using JUnit. JUnit will help us achieve automated verification of expected behavior and business logic. We are planning to use manual testing for end-to-end system testing and regression testing.

## 2 Test Cases

|Test Case # | Scenario | Steps To perform| Expected Result | Pass/Fail|
|--|-------------|-------------|-------------|-------------|
|1|Launch the application|Navigate to "Words with 6300" application on the android phone/tablet and click on the application to launch it|The application should be launched displaying the main menu screen | Pass |
|2|Verify the main menu|Launch the "words with 6300" application | The screen should consist of the application name, main menu and shows these menu options: (1) Play a word game (2) View statistics (3) Adjust the game settings | Pass |
|3|Verify the "View Statistics" Sub-Menu|Launch the "Words with 6300" application, Click on the "View statistics" on the main menu|The view statistics sub-menu with  (1) game score statistics, (2) letter statistics or (3) the word bank should be displayed | Pass |
|4|Verify "Game Score Statistics" table|Launch the "Words with 6300" application, Click on "View Statistics" on the main menu and click on "Game Statistics" |The game statistics should be displayed as the list of scores, in descending order by final game score, displaying: (a). The final game score (b). The number of turns in that game (c). The average score per turn. | Pass |
|5|View "Game settings" for a game from "Game statistics"| Launch "Words with 6300", click on "view statistics" on the main menu, click on "Game Statistics" and click on one of the table entries displayed |The settings for that game's maximum number of turns, letter distribution, and letter points should be displayed | Pass |
|6|Verify "Letter Statistics" table|Launch the "Words with 6300" application, Click on "View Statistics" on the main menu and click on "Letter Statistics" |The Letter statistics should be displayed as the list of letters, in ascending order by number of times played, displaying: (a). The total number of times that letter has been played in a word (b). The total number of times that letter has been traded back into the pool (c). The percentage of times that the letter is used in a word, out of the total number of times it has been drawn |Pass |
|7| Verify "Word Bank" table|Launch the "Words with 6300" application, Click on "View Statistics" on the main menu and click on "Word Bank" |The Word Bank should be displayed as the list of words used, starting from the most recently played, displaying: (i). The word (ii). The number of times the word has been played| Pass |
|8|Verify "Adjust the game settings" sub-menu |Launch the "Words with 6300" application, Click on the "Adjust the game settings" on the main menu|The Adjust the game settings sub-menu with  (1) Adjust the maximum number of turns, (2) Adjust the letter point and distribution should be displayed | Pass |
|9|Verify "Max Turns Adjustment"| Launch the "Words with 6300" application, Click on "Adjust the game settings" on the main menu and click on "Adjust the maximum number of turns" and insert "5" into the editText field. Click on "Change Settings". | Go back to the main menu and click on "Play a word game" (a new game). In the game screen you should see the "turns remaining" as "5" | Pass |
|10|Verify Letter point Adjustment| Launch the "Words with 6300" application, Click on "Adjust the game settings" on the main menu and click on "Adjust the letter point and distribution" and select Letter "A" from the dropdown and Select Letter Point "3". Click on "Change Settings". |  Go back to the main menu and click on "Play a word game". In the game screen you should see for any "A" tile on the rack or board contain the point value as "3" (If there is no "A" on the rack or board, swap tiles until an A appears on the board)| Pass |
|11|Verify Letter Distribution Adjustment| Launch the "Words with 6300" application, Click on "Adjust the game settings" on the main menu and click on "Adjust the letter point and distribution" and select Letter "A" from the dropdown and Select Number of Tiles as "5". Click on "Change Settings". |  Go back to the main menu and click on "Play a word game". Play till the end of the game. Follow steps in Test case#5 and verify the distribution of "A".| Pass |
|12|Verify New Game Screen| Launch the "Words with 6300" application, Click on "Play a word game" on the main menu |  The game screen should open and show the following items: Display Game score as "0", Number of Turns remaining as per the setting, A board with 4 tiles, A rack with 7 tiles, Area to play a word, Area to swap tiles| Pass |
|13|Verify Score calculation| Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Play a word by choosing a letter from board and other letters from the rack. Press Play the word. | Verify all of the following: The game score should be updated to the total points of the word played. The letter played from the board should be swapped with one of the letter played from the rack. The rack should be refilled with new letter tiles. The # of turns remaining should reduce by 1  | Pass |
|14|Verify play a word - validation 1 : Duplicate word | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Play the same word more than one time (in two different turns) by choosing a letter from board and other letters from the rack. Press Play the word. | Verify the following: you should get a pop up with header "Duplicate word" and message "This word has been already played. Please play a different word or swap tiles. ". When you press OK in the pop up, the game should return to same state as it was before you played the game. The turns/score should not get updated.   | Pass |
|15|Verify play a word - validation 2 | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Enter no letters in the word. Press Play the word. | Verify the following: you should get a pop up with header "Invalid" and message "Enter a word to play". When you press OK in the pop up, the game should return to same state as it was before you played the game. The turns/score should not get updated.   | Pass |
|16|Verify play a word - validation 3 | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Enter one more letters not available in the rack. Press Play the word. | Verify the following: you should get a pop up with header "Invalid" and message "Use only letters from rack to make the word". On click of OK on the pop up, the game screen should be shown. The turns/score should not get updated.   | Pass |
|17|Verify play a word - validation 4 | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Enter a word without using the letter from board. Press Play the word. | Verify the following: you should get a pop up with header "Invalid" and message "Must use the chosen letter from the board in the word". On click of OK on the pop up, the game screen should be shown. The turns/score should not get updated.   | Pass|
|18|Verify Swap Tiles Feature | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Enter 1- 7 letters form the rack on the swap tiles section and click on "Swap Tiles" Button | Verify the following: The selected tiles on the board should get replaced with different tiles from the pool. The game turn should get reduced by 1   | Pass|
|19|Verify Swap Tiles - validation error 1 | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Enter no letters in the swap tiles section and click on "Swap Tiles" Button | Verify the following: No action should happen. The button will not be clickable without entering letter(s). There should not be any changes on the number of turns or game score   |Pass |
|20|Verify Swap Tiles - validation error 2 | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Enter a letter which is not available on the rack in the swap tiles section and click on "Swap Tiles" Button | Verify the following: An error pop-up should be shown - "Unable to Exchange Letters : Some or all of the letters could not be exchanged. Make sure you only select letters on your rack". On click of OK on the pop up, the game screen should be shown. There should not be any changes on the number of turns or game score   | Pass|
|21|Verify Bonus Score | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Play the game until you run out the tiles in pool | Verify the following: The game should end by displaying a pop up with header "Game Over!" and message "Final Score:" and the final score as game score + 10 should be displayed on the pop up. When you click on "OK" on the pop up, you should return to main menu of the game   |Pass |
|22|Verify Final Score (without bonus) | Launch the "Words with 6300" application, Click on "Play a word game" on the main menu. Play the game until you exhaust all the turns of the game | Verify the following: The game should end by displaying a pop up with header "Game Over!" and message "Final Score:" and the final score as game score should be displayed on the pop up. When you click on "OK" on the pop up, you should return to main menu of the game   | Pass |
|23|Swap Letters - Lower case|Launch the "Words with 6300" application, Click on "Play a word game" on the main menu, Enter 1- 7 letters form the rack on the swap tiles section in Lowercase, Click on "Swap Tiles" Button|The selected tiles on the board should get replaced with different tiles from the pool. The game turn should get reduced by 1|Pass|
|24|Swap Letters - throughout the game|Launch the "Words with 6300" application, Click on "Play a word game" on the main menu, Play the game until you exhaust all the turns of the game using swap tiles|The game should end by displaying a pop up with the final score as 0 and OK button|Pass|
|25|Duplicate word check - resumed game|1. Launch the "Words with 6300" application; 2. Click on "Play a word game" on the main menu; 3. Play a word; 4. Exit the game; 5. Repeat steps 1,2; 6. Enter the word in step 4 again; 7.Press Play the word.|An error message should get displayed hinting duplicate word|Pass|
|26| Max turn error message|Run unit test from Android Studio.|User interface generates "Value must be greater than 0!" when user attempts to set the maximum number of turns to 0.|Pass|
|27|Letter points and distribution error messages|Run unit test from Android Studio.|User interface generates "Value must be greater than 0!" when user attempts to set the letter points and/or letter distribution to 0.|Pass|
|28|Run unit tests for Game|Run unit test from Android Studio.|All unit tests covering the Game should pass.| Pass          |
|29|Run unit tests for Board|Run unit test from Android Studio.|All unit tests covering the Board should pass.|Pass|
|30|Run unit tests for Rack|Run unit test from Android Studio.|All unit tests covering the Rack should pass.|Pass|
|31|Run unit tests for Letter Pool|Run unit test from Android Studio.|All unit tests covering the Letter should pass.|Pass|
|32|Audit Unit Test Coverage|Using the test coverage tool in Android Studio, inspect percentage covered results for all non-UI-focused classes.|Verify that an appropriate percentage of test coverage is achieved according to the adequacy criterion.|Pass|
|33|Run unit tests for GameSettings|Run unit test from Android Studio.|All unit tests covering the GameSettings should pass.|Pass|
