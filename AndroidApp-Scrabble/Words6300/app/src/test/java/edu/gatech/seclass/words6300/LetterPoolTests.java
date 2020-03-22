package edu.gatech.seclass.words6300;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LetterPoolTests {
    private LetterPool ClassUnderTest;

    @Before
    public void setUp() {
        List<Character> threeLetters = new ArrayList<>();
        threeLetters.add('a');
        threeLetters.add('b');
        threeLetters.add('c');
        ClassUnderTest = new LetterPool(threeLetters);
    }

    @Test
    public void adding_letters_results_in_correct_count() {
        assertEquals(3, ClassUnderTest.getLetterCount());
    }

    @Test
    public void removing_a_letter_decreases_count() {
        Character removedLetter = ClassUnderTest.getNewLetter();
        assertEquals(2, ClassUnderTest.getLetterCount());
    }

    @Test
    public void removing_all_letters_makes_count_zero() {
        ClassUnderTest.getNewLetter();
        ClassUnderTest.getNewLetter();
        ClassUnderTest.getNewLetter();
        assertEquals(0, ClassUnderTest.getLetterCount());
    }

    @Test
    public void get_letter_from_empty_pool_returns_null() {
        ClassUnderTest.getNewLetter();
        ClassUnderTest.getNewLetter();
        ClassUnderTest.getNewLetter();
        Character emptyLetter = ClassUnderTest.getNewLetter();
        assertNull(emptyLetter);
    }
}
