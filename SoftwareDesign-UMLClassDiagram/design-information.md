Requirements and Design:


1.    When the application is started, the player may choose to (1) Play a word game, (2) View statistics, or (3) Adjust the game settings.  

Design: 
In my design, the ScrabbleUI class offers these three options via a menu method.



2.    When choosing to adjust the game settings, the player (1) may choose for the game to end after a certain maximum number of turns and (2) may adjust the number of and the letter points for each letter available in the pool of letters, starting with the default matching the English Scrabble distribution (12 E’s worth 1 point each, 4 D’s worth 2 points each, etc).

Design:
(1) In my design, the ‘setMaxTurns()’ method saves the user input  to an integer ‘maxTurns’ in the GameSettings class.
(2) The loadEnglishScrabble() and setNewDistribution() methods in the GameSettings class. For convenience, I have defined a data type LetterDistribution that saves the letter symbol, points assigned to that letter, and the number of times (frequency) it will appear in the pool. A Pool of LetterDistribution type saves the distribution to be used in the game.




3.    When choosing to play a word game, a player will:
a.    Be shown a ‘board’ consisting of 4 letters drawn from the pool of available letters.
b.    Be shown a ‘rack’ of 7 letters also drawn from the pool of available letters.
c.    On each turn, up to the maximum number of turns either:
i.    Enter a word made up of one or more letters from the player’s rack of letters, and one from the board.  The word must contain only valid letters and may not be repeated within a single game, but does not need to be checked against a dictionary (for simplicity, we will assume the player enters only real words that are spelled correctly).
or
ii.    Swap 1-7 letters from their rack with letters from the pool of letters.  This is the only time letters are returned to the pool during a game.
d.    After a word is played, that letter on the board will be replaced by a different random letter from the word that was just played.  Example:  If ‘c’ is on the board, and ‘j’,’a’,’k’,’e’,’t’,’s’ are part of the rack, then the player may enter ‘jackets’ as a word, and the ‘c’ will be randomly replaced by the ‘j’,’a’,’k’,’e’,’t’, or ’s’ for the next turn on the board.
e.    After a word is played, the tiles used from the rack are replaced from the pool of letters.
f.    After a word is played, the player’s score will increase by the total number of points for the letters in the word, including the letter used from the board. (So ‘jackets’, if using default values, would score 20 points.)
g.    If the pool of letters is empty and the rack cannot be refilled, the player will score an additional 10 points.
h.    When the maximum number of turns has been played, or the pool of letters is empty and the rack cannot be refilled, the game will end, and the final score will be displayed before returning to the first menu.

Design:
a. The attribute Letters which is an array of 4 characters and a method ‘loadLettersFromPool()’ are defined in the Board class. The ‘WordGame’ class creates a Board instance using the ‘createBoard’ method when the game starts.
b. The attribute rackLetters which is an array of 7 characters and a method ‘addLettersFromPool()’ are defined in the Rack class. The ‘WordGame’ class creates a Rack instance using the ‘createRack’ method when the game starts.
c. A boolean attribute isWordPlayed is defined to initiate relevant methods when the player enters n word. ii. An integer variable nSwap (determines the number of letters to swap) and method swapLettersWithPool() are defined in the Rack class to implement this scenario. The ‘updateRack’ boolean defined the ‘WordGame’ class determines if the player gas decided to swap the rack letters.
d. ‘addLettersFromRack()’ method is defined in the Rack class to handle that functionality. The Board class is connected to both WordGame class  and Rack classes via ‘WordPlayed’ association
e. The ‘removeLetters()’ and ‘addLettersFromPool()’  methods are defined in the Rack class to realize that requirement.
f. The ‘score’ attribute (Integer) and the ‘updateScore()’ method defined in the ‘WordGame’ class handles that functionality.
g. The ‘isEmpty’ boolean in the ‘PoolOfLetters’ class along with the previously defined ‘isRackFull’ boolean and the ‘addLettersFromPool’ method in the ‘Rack’ class can handle this requirement.
h. The ‘numberOfTurnsLeft()’ method in the ‘WordGame’ class along with the previously defined ‘isRackFull’ boolean and the ‘addLettersFromPool’ method in the ‘Rack’ class can handle this requirement.




4.    A player may choose to leave a game in progress at any time.  Selecting to play a game from the menu should then return to the game in progress.

Design:
The ‘isGameOver()’ Boolean defined in the ‘WordGame’ class determines if a player has finished the game or not. The ‘InGame’ association between the ‘ScrabbleUI’ class and the ‘WordGame’ class will make sure that the player returns to the game in progress upon selection to play a game from the menu.



5.    When choosing to view statistics, the player may view (1) game score statistics, (2) letter statistics or (3) the word bank.

Design:
The ‘ViewStatistics’ association is defined between the ‘ScrabbleUI’ class and ‘GameScoreStats’, ‘LetterStats’, and ‘WordBank’ classes to implement that.



6.    For game score statistics, the player will view the list of scores, in descending order by final game score, displaying:
a.    The final game score
b.    The number of turns in that game
c.    The average score per turn
The player may select any of the game scores to view the settings for that game’s maximum number of turns, letter distribution, and letter points.

Design:
A ‘GameScore’ data type is defined for convenience. The ‘finalScore’, ‘numberOfTurns’, ‘averargeScore’, ‘timePlayed’, and ‘gameID’ variables are part of this data type that respectively save the final score (as an integer),  number of turns (as an integer), average score per turn (as a float), the game end time (as a Date type), and the game identifier (as a Date type) which is also the game start time to save the required data from each of the game when the game is completed. In the ‘GameScoreStats’ class, I have defined an array of ‘GameScore’ data type and ‘updateListOfScores()’ and ‘updateSingleGameSettings()’ methods are defined to perform the pessary functions. The ‘letterStats’ attribute and ‘setGameID()’ method are defined in the ‘WordGame’ to make sure all the necessary information are saved in the ‘GameScoreStats’ class via ‘GameOver’ association.



7.    For letter statistics, the player will view the list of letters, in ascending order by number of times played, displaying:
a.    The total number of times that letter has been played in a word
b.    The total number of times that letter has been traded back into the pool
c.    The percentage of times that the letter is used in a word, out of the total number of times it has been drawn

Design:
A ‘LetterStats’ data  type is defined for convenience. This data type saves information such as total number of times a letter is traded back in to the pool (as ‘timesTradedBack’ integer) and the total number of times the letter has been drawn (as ‘timesDrawn’ integer) in a game. In addition, we have defined another new data type ‘WordBank’ to save each of the words played in the game (as ‘Word’ of type string), time stamp of the word when it was played last time (as ‘Time’ of type Date) and the total number of times the word has been played (as ‘Count’ of type integer). I defined objects ‘letterStats’ of type LetterStats, ‘bank’ of type WordBank, ‘percTimes’ of type float and a method ‘determineLetterStats()’ to determine all the stats needed to implement this functionality. A ‘letterStats’ array of this datatype, a ‘updateLetterStats()’ method and a ‘addWords’ methods are defined in the ‘WordGame’ class to handle this requirement.




8.    For the word bank, the player will view the list of words used, starting from the most recently played, displaying:
i.    The word
ii.    The number of times the word has been played

Design:
The already defined data type ‘WordBank’ is leveraged in this step. I defined objects ‘bank’ (array of type WordBank), and two methods: ‘increaseCount()’ and ‘sortBank()’ to show the statistics required in this step. As mentioned in the implementation of (7)‘addWords’ method is defined in the ‘WordGame’ class. The ‘WordsBank’ class  is also connected to the ‘WordGame’ class via ‘GameOver’ association.



9.    The user interface must be intuitive and responsive.

Design:
This is not represented in my design since it will be entirely handled by the GUI implementation.



10.    The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.

Design:
This is not represented in my design since it will be entirely handled by the hardware.



11.    For simplicity, you may assume there is a single system running the application.

Design:
This is handled by the ‘OperatingSystem’ utility class. The ‘getDate()’ method of the ‘OperatingSystem’ class also returns the necessary ‘times’ in this application.
