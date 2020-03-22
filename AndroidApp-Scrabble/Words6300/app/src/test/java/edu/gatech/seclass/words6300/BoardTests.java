package edu.gatech.seclass.words6300;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTests {
    Board ClassUnderTest;

    @Before
    public void setUp() {
        ClassUnderTest = new Board();
    }

    @Test
    public void when_letter_is_added_then_it_is_in_output_from_getLetters() {
        ClassUnderTest.addLetter('A');
        List<Character> letters = ClassUnderTest.getLetters();
        assertTrue(letters.contains('A'));
    }

    @Test(expected = IllegalStateException.class)
    public void when_letter_is_added_to_full_Board_then_correct_exception_is_thrown() {
        ClassUnderTest.addLetter('A');
        ClassUnderTest.addLetter('A');
        ClassUnderTest.addLetter('A');
        ClassUnderTest.addLetter('A');
        ClassUnderTest.addLetter('A');
    }

    @Test(expected = IllegalStateException.class)
    public void when_replacing_letter_that_is_not_on_Board_then_the_correct_exception_is_thrown() {
        ClassUnderTest.addLetter('A');
        ClassUnderTest.replaceLetter('Z', 'Q');
    }

    @Test
    public void when_replacing_letter_with_itself_then_Board_is_unchanged() {
        ClassUnderTest.addLetter('A');
        List<Character> before = ClassUnderTest.getLetters();
        ClassUnderTest.replaceLetter('A', 'A');
        List<Character> after = ClassUnderTest.getLetters();
        assertEquals(before, after);
    }

    @Test
    public void when_replacing_letter_with_new_letter_then_the_new_letter_is_in_output_from_getLetters() {
        ClassUnderTest.addLetter('A');
        ClassUnderTest.replaceLetter('A', 'B');
        List<Character> letters = ClassUnderTest.getLetters();
        assertTrue(letters.contains('B'));
    }
}
