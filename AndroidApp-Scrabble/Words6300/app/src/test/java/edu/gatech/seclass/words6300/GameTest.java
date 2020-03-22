package edu.gatech.seclass.words6300;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    Game ClassUnderTest;
    Board board;
    @Before
    public void setUp() {
        LetterPool letterPool;
        List<Character> someLetters = new ArrayList<>();
        someLetters.add('D');
        someLetters.add('H');
        someLetters.add('O');
        someLetters.add('U');
        someLetters.add('W');
        someLetters.add('Y');
        letterPool = new LetterPool(someLetters);
        //Board board;
        GameSettings testGameSettings;

        List<LetterSetting> settingsList = new ArrayList<>();
        settingsList.add(new LetterSetting('D', 1, 6));
        settingsList.add(new LetterSetting('H', 2, 5));
        settingsList.add(new LetterSetting('O', 3, 4));
        settingsList.add(new LetterSetting('U', 4, 3));
        settingsList.add(new LetterSetting('W', 5, 2));
        settingsList.add(new LetterSetting('Y', 6, 1));

        testGameSettings = new GameSettings(10);
        //ClassUnderTest = new Game(10, testGameSettings);
        ClassUnderTest = new Game(10, testGameSettings);
    }

    @Test
    public void getScore() {
        ClassUnderTest.playWord("UTOPIA");
        assertEquals(8, ClassUnderTest.getScore());
    }

    @Test
    public void finishGameWithoutBonusPoints() {
        GameSettings testGameSettings = new GameSettings(1);
        ClassUnderTest = new Game(90, testGameSettings);
        ClassUnderTest.playWord("UTOPIA");
        ClassUnderTest.canContinue();
        assertEquals(8, ClassUnderTest.getScore());
    }


    @Test
    public void getTurnCount() {
        ClassUnderTest.playWord("HOWDY");
        ClassUnderTest.playWord("YOU");
        ClassUnderTest.playWord("DO");
        assertEquals(3, ClassUnderTest.getTurnCount());
    }

    @Test
    public void canPlayWord() {
        ClassUnderTest.playWord("HOWDY");
        assertEquals(false, ClassUnderTest.canPlayWord("HOWDY"));
    }

    @Test (expected = IllegalStateException.class)
    public void addSameWordTwice() {
        ClassUnderTest.playWord("HOWDY");
        ClassUnderTest.playWord("HOWDY");
    }

    @Test
    public void canDrawLetters() {
        assertEquals(false, ClassUnderTest.canDrawLetters(100));
    }


    @Test
    public void canContinue() {
        GameSettings testGameSettings = new GameSettings(2);
        ClassUnderTest = new Game(100, testGameSettings);
        ClassUnderTest.playWord("HOWDY");
        ClassUnderTest.playWord("YOU");

        assertEquals(false, ClassUnderTest.canContinue());
    }

    @Test
    public void getRackLetters() {
        assertEquals(7, ClassUnderTest.getRackLetters().size());
    }

    @Test
    public void getBoardLetters() {
        assertEquals(4, ClassUnderTest.getBoardLetters().size());
    }
}