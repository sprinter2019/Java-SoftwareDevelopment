package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private long gameId;
    private List<String> wordBank;
    private LetterPool pool;
    private Rack rack;
    private Board board;
    private GameSettings gameSettings;
    private int score;
    private int turnCount;

    public Game(long gameId, GameSettings gameSettings) {
        this.gameId = gameId;
        this.gameSettings = gameSettings;
        wordBank = new ArrayList<>();

        pool = BuildLetterPool(this.gameSettings);
        board = BuildBoard(pool);
        rack = BuildRack(pool);
    }

    public Game(long gameId, GameSettings gameSettings, List<String> wordBank, LetterPool pool, Board board, Rack rack, int score, int turnCount) {
        this.gameId = gameId;
        this.gameSettings = gameSettings;
        this.wordBank = wordBank;

        this.pool = pool;
        this.board = board;
        this.rack = rack;

        this.score = score;
        this.turnCount = turnCount;
    }

    public int getScore() {
        return score;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public int getRemainingTurns() {
        return gameSettings.getMaxTurns() - turnCount;
    }

    public boolean canPlayWord(String word) {
        return !wordBank.contains(word);
    }

    public boolean canPlayWord(String word, Character boardLetter) {
        if (wordBank.contains(word)) {
            return false;
        }

        if (!board.getLetters().contains(boardLetter)) {
            return false;
        }

        List<Character> lettersPlayedFromRack = getRackLettersFromWord(word, boardLetter);
        if (lettersPlayedFromRack.size() == 0) {
            return false;
        }

        List<Character> rackLetters = new ArrayList<>(rack.getLetters());
        for (Character c : lettersPlayedFromRack) {
            if (!rackLetters.contains(c)) {
                return false;
            }
            rackLetters.remove(c);
        }

        return true;
    }

    public int playWord(String word) {
        if (!canPlayWord(word)) {
            throw new IllegalStateException("Word \"" + word + "\" already played.");
        }
        turnCount++;
        wordBank.add(word);
        int wordScore = getScoreForWord(word);
        score += wordScore;
        return wordScore;
    }

    public List<Character> playWord(String word, Character boardLetter) {
        if (!canPlayWord(word, boardLetter)) {
            throw new IllegalStateException("Word \"" + word + "\" cannot be played.");
        }

        List<Character> lettersPlayedList = getRackLettersFromWord(word, boardLetter);

        rack.removeLetters(lettersPlayedList);

        int size = lettersPlayedList.size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(size);
        Character replacementLetter = lettersPlayedList.get(randomIndex);
        board.replaceLetter(boardLetter, replacementLetter);

        List<Character> newLetters = new ArrayList<>();
        while (rack.hasExtraCapacity()) {
            Character newLetter = pool.getNewLetter();
            if (newLetter == null) {
                break;
            }
            rack.addLetter(newLetter);
            newLetters.add(newLetter);
        }

        turnCount++;
        wordBank.add(word);
        int wordScore = getScoreForWord(word);
        score += wordScore;
        return newLetters;
    }

    public boolean canDrawLetters(int numLettersToDraw) {
        return pool.getLetterCount() >= numLettersToDraw;
    }

    public List<Character> drawNewLetters(List<Character> lettersToExchange) {
        if (lettersToExchange.size() == 0) return lettersToExchange;

        rack.removeLetters(lettersToExchange);

        List<Character> newLetters = new ArrayList<>();
        for (int i = 0; i < lettersToExchange.size(); i++) {
            Character newLetter = pool.getNewLetter();
            rack.addLetter(newLetter);
            newLetters.add(newLetter);
        }

        for (char c : lettersToExchange) {
            pool.addLetter(c);
        }

        turnCount++;
        return newLetters;
    }

    public boolean canContinue() {
        // Reached max turns?
        if (turnCount == gameSettings.getMaxTurns()) {
            // score+=10;
            return false;
        }
        // Is letter pool empty?
        if (!canDrawLetters(1)) {
            score += 10;
            return false;
        }

        return true;
    }

    public List<Character> getRackLetters() {
        return rack.getLetters();
    }

    public List<Character> getBoardLetters() {
        return board.getLetters();
    }

    public List<Character> getPoolLetters() {
        return pool.getPoolLetters();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    private LetterPool BuildLetterPool(GameSettings settings) {
        List<Character> letterList = new ArrayList<>();
        for (char letter : LetterUtilities.GetAlphabetAsList()) {
            int letterFrequency = settings.getLetterFrequency(letter);
            for (int i = 0; i < letterFrequency; i++) {
                letterList.add(letter);
            }
        }

        return new LetterPool(letterList);
    }

    private Board BuildBoard(LetterPool letterPool) {
        Board b = new Board();
        for (int i = 0; i < 4; i++) {
            b.addLetter(letterPool.getNewLetter());
        }
        return b;
    }

    private Rack BuildRack(LetterPool letterPool) {
        int rackCapacity = 7;
        Rack r = new Rack(rackCapacity);
        for (int i = 0; i < rackCapacity; i++) {
            r.addLetter(letterPool.getNewLetter());
        }

        return r;
    }

    private int getScoreForWord(String word) {
        int wordScore = 0;
        for (Character c : word.toCharArray()) {
            wordScore += gameSettings.getLetterPoints(c);
        }
        return wordScore;
    }

    private List<Character> getRackLettersFromWord(String word, Character boardLetter) {
        char[] lettersPlayedFromRack = word.replaceFirst(boardLetter.toString(), "").toCharArray();
        return LetterUtilities.getCharacterListFromArray(lettersPlayedFromRack);
    }

    public long getGameId() {
        return gameId;
    }
}
