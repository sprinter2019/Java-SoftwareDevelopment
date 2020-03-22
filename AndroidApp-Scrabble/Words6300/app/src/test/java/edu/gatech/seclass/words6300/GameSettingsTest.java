package edu.gatech.seclass.words6300;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameSettingsTest {

    GameSettings ClassUnderTest;
    @Before
    public void setUp() {
        List<LetterSetting> settingsList = new ArrayList<>();
        settingsList.add(new LetterSetting('A', 9, 1));
        settingsList.add(new LetterSetting('B', 8, 2));
        settingsList.add(new LetterSetting('C', 7, 3));
        ClassUnderTest = new GameSettings(50, settingsList);
    }

    @Test
    public void getAndSetMaxTurns() {
        ClassUnderTest.setMaxTurns(5);
        assertEquals(5, ClassUnderTest.getMaxTurns());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidMaxTurns1(){
        ClassUnderTest.setMaxTurns(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidMaxTurns2(){
        ClassUnderTest.setMaxTurns(-20);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidLetterFrequency() {
        ClassUnderTest.updateLetterFrequency('A', -10);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidLetterInput() {
        List<LetterSetting> settingsList = new ArrayList<>();
        settingsList.add(new LetterSetting('@', 9, 1));
        ClassUnderTest = new GameSettings(10, settingsList);
    }

    @Test
    public void updateAndGetLetterFrequency() {
        ClassUnderTest.updateLetterFrequency('C', 10);
        assertEquals(10, ClassUnderTest.getLetterFrequency('C'));
    }
    @Test
    public void updateAndGetLetterPoints() {
        ClassUnderTest.updateLetterPoints('A', 7);
        assertEquals(7, ClassUnderTest.getLetterPoints('A'));
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateAInvalidLetterPoints() {
        ClassUnderTest.updateLetterPoints('A', -100);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateAInvalidLetterFrequency() {
        ClassUnderTest.updateLetterFrequency('A', -10);
    }

}