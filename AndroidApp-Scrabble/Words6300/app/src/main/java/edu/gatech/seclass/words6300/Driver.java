package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver {
    private Map<Integer, GameStatistics> gameStatisticsMap;
    private Map<Character, LetterStatistics> letterStatisticsMap;
    private GameSettings nextGameSettings;
    private Game currentGame;
    private DatabaseAccessHelper databaseAccessHelper;
    private int gameId = 1;

    public Driver(DatabaseAccessHelper helper) {
        databaseAccessHelper = helper;
        currentGame = databaseAccessHelper.getGame();
        saveGame(true);
        nextGameSettings = currentGame.getGameSettings();


        letterStatisticsMap = databaseAccessHelper.getLetterStatisticsMap();
        gameStatisticsMap = new HashMap<>();
    }

    public void createNewGame() {
        currentGame = databaseAccessHelper.getGame();
        saveGame(true);
    }

    public void updateMaxTurns(int newTurns) {
        nextGameSettings.setMaxTurns(newTurns);
    }

    public void updateLetterFrequency(char letter, int frequency) {
        nextGameSettings.updateLetterFrequency(letter, frequency);
    }

    public void updateLetterPoints(char letter, int points) {
        nextGameSettings.updateLetterPoints(letter, points);
    }

    public int getMaxTurns() {
        return nextGameSettings.getMaxTurns();
    }

    public int getRemainingTurns() {
        return currentGame.getRemainingTurns();
    }

    public int getCurrentScore() {
        return currentGame.getScore();
    }

    public int getLetterFrequency(char letter) {
        return nextGameSettings.getLetterFrequency(letter);
    }

    public int getLetterPoints(char letter) {
        return nextGameSettings.getLetterPoints(letter);
    }

    public boolean isGameInProgress() {
        if (currentGame == null) {
            return false;
        }
        return true;
    }

    public List<Character> getRackLetters() {
        if (!isGameInProgress()) {
            return new ArrayList<>();
        }

        return currentGame.getRackLetters();
    }

    public List<Character> getBoardLetters() {
        if (!isGameInProgress()) {
            return new ArrayList<>();
        }

        return currentGame.getBoardLetters();
    }

    public int playWord(String word) {
        if (!isGameInProgress()) {
            return 0;
        }

        return currentGame.playWord(word);
    }

    public void playWord(String word, Character boardLetter) {
        if (!isGameInProgress()) {
            return;
        }

        List<Character> newLetters = currentGame.playWord(word, boardLetter);

        trackWordPlay(word);
        // Track letter statistics for play
        for (Character c : word.toCharArray()) {
            addLetterPlay(c);
        }
        for (Character c : newLetters) {
            addLetterDraw(c);
        }
        saveGame(true);
    }

    public boolean canExchangeLetters(List<Character> lettersToReplace) {
        List<Character> remainingLettersToCheck = new ArrayList<>(currentGame.getRackLetters());
        for (Character c : lettersToReplace) {
            if (!remainingLettersToCheck.contains(c)) {
                return false;
            }
            remainingLettersToCheck.remove(c);
        }
        return true;
    }

    public void drawNewLetters(List<Character> lettersToReplace) {
        if (!isGameInProgress()) {
            return;
        }

        for (Character c : lettersToReplace) {
            addLetterTrade(c);
        }

        List<Character> newLetters = currentGame.drawNewLetters(lettersToReplace);
        for (Character c : newLetters) {
            addLetterDraw(c);
        }

        saveGame(true);
    }

    public boolean canPlayWord(String word, Character boardLetter) {
        if (!isGameInProgress()) {
            return false;
        }

        return currentGame.canPlayWord(word, boardLetter);
    }

    public boolean canContinue() {
        if (!isGameInProgress()) {
            return false;
        }

        return currentGame.canContinue();
    }

    public void finishGame() {
        int finalScore = getCurrentScore();
        int remainingTurns = currentGame.getRemainingTurns();
        GameSettings settings = currentGame.getGameSettings();
        addGameStats(finalScore, remainingTurns, settings);
        saveGame(false);
        // createNewGame();
    }

    private void addGameStats(int score, int turns, GameSettings settings) {
        GameStatistics stats = new GameStatistics(score, turns, settings);
        gameStatisticsMap.put(gameId, stats);
    }

    private void addLetterPlay(char letter) {
        LetterStatistics stats = letterStatisticsMap.get(letter);
        stats.addPlay();
        databaseAccessHelper.updateLetterStatistic(stats);
    }

    private void addLetterDraw(char letter) {
        LetterStatistics stats = letterStatisticsMap.get(letter);
        stats.addDraw();
        databaseAccessHelper.updateLetterStatistic(stats);
    }

    private void addLetterTrade(char letter) {
        LetterStatistics stats = letterStatisticsMap.get(letter);
        stats.addTrade();
        databaseAccessHelper.updateLetterStatistic(stats);
    }

    private void saveGame(boolean isInProgress) {
        int id = (int) currentGame.getGameId();
        String rack = LetterUtilities.getStringFromCharacterList(currentGame.getRackLetters());
        String pool = LetterUtilities.getStringFromCharacterList(currentGame.getPoolLetters());
        int score = currentGame.getScore();
        String board = LetterUtilities.getStringFromCharacterList(currentGame.getBoardLetters());
        int turnCount = currentGame.getTurnCount();

        int inProgressBit = isInProgress ? 1 : 0;
        databaseAccessHelper.saveGameProgress(id, rack, pool, score, board, turnCount, inProgressBit);
    }

    private void trackWordPlay(String word) {
        int gameId = (int) currentGame.getGameId();
        long dateTime = new Date().getTime();
        databaseAccessHelper.updateWordBankWithWord(gameId, word, dateTime);
    }
}
