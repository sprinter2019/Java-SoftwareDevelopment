package edu.gatech.seclass.words6300;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RackTests {
    private Rack ClassUnderTest;

    @Test(expected = IllegalArgumentException.class)
    public void creating_Rack_with_zero_capacity_throws_correct_exception() {
        new Rack(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creating_Rack_with_negative_capacity_throws_correct_exception() {
        new Rack(-1);
    }

    @Test
    public void Rack_has_capacity_when_initialized() {
        ClassUnderTest = new Rack(1);
        assertTrue(ClassUnderTest.hasExtraCapacity());
    }

    @Test
    public void empty_list_returned_when_no_letters_added() {
        ClassUnderTest = new Rack(1);
        List<Character> result = ClassUnderTest.getLetters();
        assertTrue(result.isEmpty());
    }

    @Test
    public void when_letter_added_it_is_returned_by_getLetters() {
        ClassUnderTest = new Rack(1);
        ClassUnderTest.addLetter('A');
        List<Character> letters = ClassUnderTest.getLetters();
        assertTrue(letters.contains('A'));
    }

    @Test
    public void when_letter_reaches_capacity_then_hasExtraCapacity_is_false() {
        ClassUnderTest = new Rack(1);
        ClassUnderTest.addLetter('A');
        assertFalse(ClassUnderTest.hasExtraCapacity());
    }

    @Test(expected = IllegalStateException.class)
    public void when_adding_a_letter_to_a_full_Rack_the_correct_exception_is_thrown() {
        ClassUnderTest = new Rack(1);
        ClassUnderTest.addLetter('A');
        ClassUnderTest.addLetter('A');
    }

    @Test
    public void when_a_letter_is_added_multiple_times_it_is_on_the_rack_multiple_times() {
        ClassUnderTest = new Rack(2);
        ClassUnderTest.addLetter('A');
        ClassUnderTest.addLetter('A');
        for (Character c : ClassUnderTest.getLetters()) {
            if (c != 'A') {
                fail();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_removing_letters_from_empty_Rack_the_correct_exception_is_thrown() {
        ClassUnderTest = new Rack(1);
        List<Character> lettersToRemove = new ArrayList<>();
        lettersToRemove.add('A');
        ClassUnderTest.removeLetters(lettersToRemove);
    }

    @Test
    public void when_removing_letter_from_full_Rack_then_Rack_has_capacity() {
        ClassUnderTest = new Rack(1);
        ClassUnderTest.addLetter('A');
        List<Character> lettersToRemove = new ArrayList<>();
        lettersToRemove.add('A');
        ClassUnderTest.removeLetters(lettersToRemove);
        assertTrue(ClassUnderTest.hasExtraCapacity());
    }

    @Test
    public void when_removing_letter_that_appears_multiple_times_only_one_is_removed() {
        Character expectedLetter = 'A';
        ClassUnderTest = new Rack(2);
        ClassUnderTest.addLetter(expectedLetter);
        ClassUnderTest.addLetter(expectedLetter);
        List<Character> lettersToRemove = new ArrayList<>();
        lettersToRemove.add(expectedLetter);
        ClassUnderTest.removeLetters(lettersToRemove);
        List<Character> remainingLetters = ClassUnderTest.getLetters();
        assertEquals(expectedLetter, remainingLetters.get(0));
    }
}
