package edu.gatech.seclass.words6300;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    private TextView textViewScore;
    private TextView textViewTurns;
    private TextView textViewBoardLetter1;
    private TextView textViewBoardLetter2;
    private TextView textViewBoardLetter3;
    private TextView textViewBoardLetter4;
    private TextView textViewBoardLetterPt1;
    private TextView textViewBoardLetterPt2;
    private TextView textViewBoardLetterPt3;
    private TextView textViewBoardLetterPt4;
    private TextView textViewRackLetter1;
    private TextView textViewRackLetter2;
    private TextView textViewRackLetter3;
    private TextView textViewRackLetter4;
    private TextView textViewRackLetter5;
    private TextView textViewRackLetter6;
    private TextView textViewRackLetter7;
    private TextView textViewRackLetterPt1;
    private TextView textViewRackLetterPt2;
    private TextView textViewRackLetterPt3;
    private TextView textViewRackLetterPt4;
    private TextView textViewRackLetterPt5;
    private TextView textViewRackLetterPt6;
    private TextView textViewRackLetterPt7;
    private Spinner boardLetterSpinner;
    private EditText editTextWord;
    private EditText editTextTiles;

    private Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        DatabaseAccessHelper databaseAccessHelper = DatabaseAccessHelper.getInstance(this);
        driver = new Driver(databaseAccessHelper);

        textViewScore = findViewById(R.id.textViewScore);
        textViewTurns = findViewById(R.id.textViewTurns);
        textViewBoardLetter1 = findViewById(R.id.textViewBoardLetter1);
        textViewBoardLetter2 = findViewById(R.id.textViewBoardLetter2);
        textViewBoardLetter3 = findViewById(R.id.textViewBoardLetter3);
        textViewBoardLetter4 = findViewById(R.id.textViewBoardLetter4);
        textViewBoardLetterPt1 = findViewById(R.id.textViewBoardLetterPt1);
        textViewBoardLetterPt2 = findViewById(R.id.textViewBoardLetterPt2);
        textViewBoardLetterPt3 = findViewById(R.id.textViewBoardLetterPt3);
        textViewBoardLetterPt4 = findViewById(R.id.textViewBoardLetterPt4);
        textViewRackLetter1 = findViewById(R.id.textViewRackLetter1);
        textViewRackLetter2 = findViewById(R.id.textViewRackLetter2);
        textViewRackLetter3 = findViewById(R.id.textViewRackLetter3);
        textViewRackLetter4 = findViewById(R.id.textViewRackLetter4);
        textViewRackLetter5 = findViewById(R.id.textViewRackLetter5);
        textViewRackLetter6 = findViewById(R.id.textViewRackLetter6);
        textViewRackLetter7 = findViewById(R.id.textViewRackLetter7);
        textViewRackLetterPt1 = findViewById(R.id.textViewRackLetterPt1);
        textViewRackLetterPt2 = findViewById(R.id.textViewRackLetterPt2);
        textViewRackLetterPt3 = findViewById(R.id.textViewRackLetterPt3);
        textViewRackLetterPt4 = findViewById(R.id.textViewRackLetterPt4);
        textViewRackLetterPt5 = findViewById(R.id.textViewRackLetterPt5);
        textViewRackLetterPt6 = findViewById(R.id.textViewRackLetterPt6);
        textViewRackLetterPt7 = findViewById(R.id.textViewRackLetterPt7);
        boardLetterSpinner = findViewById(R.id.spinner);
        editTextWord = findViewById(R.id.editTextWord);
        editTextTiles = findViewById(R.id.editTextTiles);

        // Setup game with data from Driver
        RefreshGameView();
    }

    public void playWord(View view) {
        Character spinnerLetter = (Character) boardLetterSpinner.getSelectedItem();

        String wordToPlay = editTextWord.getText().toString().toUpperCase();
        boolean canPlay = wordToPlay.contains(spinnerLetter.toString()) && driver.canPlayWord(wordToPlay, spinnerLetter);

        if (!canPlay) {
            displayAlert("Unable to play word",
                    "Word \"" + wordToPlay + "\" cannot be played.\n\n" +
                            "Make sure your word contains the selected board letter and all other letters are on the rack.\n\n"
                            + "Also make sure you only play a word one time per game.");
            return;
        }

        driver.playWord(wordToPlay, spinnerLetter);

        if (!driver.canContinue()) {

            driver.finishGame();
            // Display end of game alert
            displayGameOverAlert();
        }

        RefreshGameView();
    }

    private void displayGameOverAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("Final Score: " + driver.getCurrentScore())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToMain();
                    }
                })
                .create()
                .show();
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void swapLetters(View view) {
        String lettersToExchange = editTextTiles.getText().toString();
        List<Character> exchangeList = LetterUtilities.getCharacterListFromArray(lettersToExchange.toUpperCase().toCharArray());

        if (!driver.canExchangeLetters(exchangeList)) {
            displayAlert("Unable to Exchange Letters",
                    "Some or all of the letters could not be exchanged. Make sure you only select letters on your rack.");
            return;
        }

        driver.drawNewLetters(exchangeList);
        if (!driver.canContinue()) {
            driver.finishGame();
            // Display end of game alert
            displayGameOverAlert();
        } else {
            RefreshGameView();
        }
    }

    private void setupBoard(Driver driver) {
        List<Character> boardLetters = driver.getBoardLetters();
        Character letter1 = boardLetters.get(0);
        int points1 = driver.getLetterPoints(letter1);
        textViewBoardLetter1.setText(String.valueOf(letter1));
        textViewBoardLetterPt1.setText(String.valueOf(points1));

        Character letter2 = boardLetters.get(1);
        int points2 = driver.getLetterPoints(letter2);
        textViewBoardLetter2.setText(String.valueOf(letter2));
        textViewBoardLetterPt2.setText(String.valueOf(points2));

        Character letter3 = boardLetters.get(2);
        int points3 = driver.getLetterPoints(letter3);
        textViewBoardLetter3.setText(String.valueOf(letter3));
        textViewBoardLetterPt3.setText(String.valueOf(points3));

        Character letter4 = boardLetters.get(3);
        int points4 = driver.getLetterPoints(letter4);
        textViewBoardLetter4.setText(String.valueOf(letter4));
        textViewBoardLetterPt4.setText(String.valueOf(points4));
    }

    private void setupRack(Driver driver) {
        List<Character> rackLetters = driver.getRackLetters();
        Character letter1 = rackLetters.get(0);
        int points1 = driver.getLetterPoints(letter1);
        textViewRackLetter1.setText(String.valueOf(letter1));
        textViewRackLetterPt1.setText(String.valueOf(points1));

        Character letter2 = rackLetters.get(1);
        int points2 = driver.getLetterPoints(letter2);
        textViewRackLetter2.setText(String.valueOf(letter2));
        textViewRackLetterPt2.setText(String.valueOf(points2));

        Character letter3 = rackLetters.get(2);
        int points3 = driver.getLetterPoints(letter3);
        textViewRackLetter3.setText(String.valueOf(letter3));
        textViewRackLetterPt3.setText(String.valueOf(points3));

        Character letter4 = rackLetters.get(3);
        int points4 = driver.getLetterPoints(letter4);
        textViewRackLetter4.setText(String.valueOf(letter4));
        textViewRackLetterPt4.setText(String.valueOf(points4));

        Character letter5 = rackLetters.get(4);
        int points5 = driver.getLetterPoints(letter5);
        textViewRackLetter5.setText(String.valueOf(letter5));
        textViewRackLetterPt5.setText(String.valueOf(points5));

        Character letter6 = rackLetters.get(5);
        int points6 = driver.getLetterPoints(letter6);
        textViewRackLetter6.setText(String.valueOf(letter6));
        textViewRackLetterPt6.setText(String.valueOf(points6));

        Character letter7 = rackLetters.get(6);
        int points7 = driver.getLetterPoints(letter7);
        textViewRackLetter7.setText(String.valueOf(letter7));
        textViewRackLetterPt7.setText(String.valueOf(points7));
    }

    private void setupSpinner(Driver driver) {
        List<Character> boardLetters = driver.getBoardLetters();
        ArrayAdapter<Character> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, boardLetters);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        boardLetterSpinner.setAdapter(dataAdapter);
    }

    private void RefreshGameView() {
        setupBoard(driver);
        setupRack(driver);
        textViewScore.setText(String.valueOf(driver.getCurrentScore()));
        textViewTurns.setText(String.valueOf(driver.getRemainingTurns()));
        setupSpinner(driver);
        editTextWord.setText("");
        editTextTiles.setText("");
    }

    public void displayAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
