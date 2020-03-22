package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing class created for use in Georgia Tech CS6300, for posting only in assigned private Georgia Tech repositories.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * You should implement your tests in this class.  Do not change the test names.
 */

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    //Test Purpose: This is the first instructor example test.
    @Test
    public void testMostCommonChar1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals('t', mycustomstring.mostCommonChar());
    }

    @Test (expected = NullPointerException.class)
    //Test NullPointerException
    public void testMostCommonChar2() {
        //fail("Not yet implemented");
        mycustomstring.setString(null);
        mycustomstring.mostCommonChar();
    }

    //Test if the method mostCommonChar() is case insensitive
    @Test
    public void testMostCommonChar3() {
       // fail("Not yet implemented");
        mycustomstring.setString("xx!~@XXaaa&% $ Xx aa aaXXX??xxx*012 3");
        assertEquals('x', mycustomstring.mostCommonChar());
    }

    //test if the method mostCommonChar() works on numeric characters
    @Test
    public void testMostCommonChar4() {
        //fail("Not yet implemented");
        mycustomstring.setString("11 '&$%*#1122 aaab bbcc||~'cccc'cc@@@##11111");
        assertEquals('1', mycustomstring.mostCommonChar());
    }

    //Test if the method mostCommonChar() works when a non-alpha numeric character is the most common
    @Test
    public void testMostCommonChar5() {
        // fail("Not yet implemented");
        mycustomstring.setString("aAAbcdD ^^^^^^^^@@@@@ %%%%");
        assertEquals('a', mycustomstring.mostCommonChar());
    }

    //Test if the method mostCommonChar() works when multiple alphanumeric characters appear an equal number of times
    @Test
    public void testMostCommonChar6() {
        // fail("Not yet implemented");
        mycustomstring.setString("z aawZaa bA9 c A z xxxxxx ZzZ 555555");
        assertEquals('z', mycustomstring.mostCommonChar());
    }




    //Test Purpose: This is the second instructor example test.
    @Test
    public void testFilterLetters1() {
        mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("1234!!! H3y, L3t'5 put 50m 161ts in this 5tr1n6!11!1", mycustomstring.filterLetters('E', false));
    }

    //Test Purpose: This the third instructor example test.
    @Test
    public void testFilterLetters2() {
        mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("1234!!! 3, 3'5  50 d161   516!11!1", mycustomstring.filterLetters('E', true));
    }

    // Test IllegalArgumentException
    @Test (expected =IllegalArgumentException.class)
    public void testFilterLetters3() {
        
        //fail("Not yet implemented");
        mycustomstring.setString("Test String");
        mycustomstring.filterLetters('9', true);
    }

    //Test NullPointerException on the filterLetters method when thr input String is null
    @Test (expected = NullPointerException.class)
    public void testFilterLetters4() {
        //fail("Not yet implemented");
        mycustomstring.setString(null);
        mycustomstring.filterLetters('x', true);
    }

    // Test NullPointerException when n is not a letter and the input string is also null
    @Test (expected = NullPointerException.class)
    public void testFilterLetters5() {
        //fail("Not yet implemented");
        mycustomstring.setString(null);
        mycustomstring.filterLetters('@', true);
    }

    //Test if testFilterLetters() method works on a small string when more=true
    @Test
    public void testFilterLetters6() {
        //fail("Not yet implemented");
        mycustomstring.setString("A small test string 012");
        assertEquals("A mall e ring 012", mycustomstring.filterLetters('S', true));

    }

    //Test if testFilterLetters() method works on a small string when more=false
    @Test
    public void testFilterLetters7() {
        //fail("Not yet implemented");
        mycustomstring.setString("A small test string 012");
        assertEquals("  tt t 012", mycustomstring.filterLetters('S', false));
    }

    //Test if testFilterLetters() method works on a large string when more=true
    @Test
    public void testFilterLetters8() {
        //fail("Not yet implemented");
        mycustomstring.setString("A large t3st string: xyz @@ 012 ** 9Q 3$ }{|ast| xyz ??? A large t3st string: xyz @@ 012 ** 9Q 3$ }{|ast| xyz");
        assertEquals("A a 3 :  @@ 012 ** 9 3$ }{|a|  ??? A a 3 :  @@ 012 ** 9 3$ }{|a| ", mycustomstring.filterLetters('c', true));
    }

    //Test if testFilterLetters() method works on a large string when more=false
    @Test
    public void testFilterLetters9() {
        //fail("Not yet implemented");
        mycustomstring.setString("A large t3st string: xyz @@ 012 ** 9Q 3$ }{|ast| xyz ??? A large t3st string: xyz @@ 012 ** 9Q 3$ }{|ast| xyz");
        assertEquals("  3 : z @@ 012 ** 9 3$ }{|| z ???   3 : z @@ 012 ** 9 3$ }{|| z", mycustomstring.filterLetters('y', false));
    }

    //Test if testFilterLetters() method is case insensitive (small string)
    @Test
    public void testFilterLetters10() {
        //fail("Not yet implemented");
        mycustomstring.setString("A sMall t3st '$tRiNg'?");
        assertEquals("  3 '$'?", mycustomstring.filterLetters('a', true));
    }

    //Test if testFilterLetters() method is case insensitive (large string)
    @Test
    public void testFilterLetters11() {
        //fail("Not yet implemented");
        mycustomstring.setString("A lARgE t3st '$tRiNg : dTTTttdas KK 23$$%Q PP pp eSeS56 27xy6^^^ 123)S$( %asddf HGGF ZYtTUVW");
        assertEquals("  3 '$ :   23$$%   56 27xy6^^^ 123)$( %  ZYUVW", mycustomstring.filterLetters('T', false));
    }

    //Test if the testFilterLetters() method works: Some random string
    @Test
    public void testFilterLetters12() {
        //fail("Not yet implemented");
        mycustomstring.setString("S$om3 RaNd0m t3st '$tRiNg : ```--sfG-++TuZ+@@IoR@##PPk##Lq W w#&&mNB&&$><sad$$:EE33ee:%as%Y%");
        assertEquals("$3 0 3 '$ : ```---++Z+@@@#### W w#&&&&$><$$:33:%%Y%", mycustomstring.filterLetters('u', false));
    }


    //Test Purpose: This is the fourth instructor example test.
    @Test
    public void testConvertDigitsToProductsInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put 50me 123 d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToProductsInSubstring(17, 27);
        assertEquals("I'd b3tt3r put 50me 6 d61ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test Purpose: This is the fifth instructor example test, demonstrating a test for an exception.  Your exceptions should be tested in this format.
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToProductsInSubstring2() {
        mycustomstring.convertDigitsToProductsInSubstring(2, 10);
    }

    @Test (expected = IllegalArgumentException.class)
    // Test IllegalArgumentException on the testConvertDigitsToProductsInSubstring method
    public void testConvertDigitsToProductsInSubstring3() {
        //fail("Not yet implemented");
        mycustomstring.setString("Test123 String");
        mycustomstring.convertDigitsToProductsInSubstring(0, 5);
    }

    @Test (expected = IllegalArgumentException.class)
    // Test IllegalArgumentException on the testConvertDigitsToProductsInSubstring method
    public void testConvertDigitsToProductsInSubstring4() {
        //fail("Not yet implemented");
        mycustomstring.setString("Test123 String");
        mycustomstring.convertDigitsToProductsInSubstring(5, 2);
    }

    //Test MyIndexOutOfBoundsException on the testConvertDigitsToProductsInSubstring method when endposition is greater than the input string length
    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToProductsInSubstring5() {
        //fail("Not yet implemented");
        mycustomstring.setString(" A test string");
        mycustomstring.convertDigitsToProductsInSubstring(1, 20);
    }

    // Test if the testConvertDigitsToProductsInSubstring method works when both start and end positions are same
    @Test
    public void testConvertDigitsToProductsInSubstring6() {
        //fail("Not yet implemented");
        mycustomstring.setString("0234 abc 1234567 ### 04");
        mycustomstring.convertDigitsToProductsInSubstring(2, 2);
        assertEquals("0234 abc 1234567 ### 04", mycustomstring.getString());
    }

    //Test if the testConvertDigitsToProductsInSubstring method works when no digits appear in the string
    @Test
    public void testConvertDigitsToProductsInSubstring7() {
        //fail("Not yet implemented");
        mycustomstring.setString("##asdad %% abc fdffdnf ### &&&fsdfjj ");
        mycustomstring.convertDigitsToProductsInSubstring(1, 15);
        assertEquals("##asdad %% abc fdffdnf ### &&&fsdfjj ", mycustomstring.getString());
    }

    //Test if the testConvertDigitsToProductsInSubstring method works when a number has at least 15 digits
    @Test
    public void testConvertDigitsToProductsInSubstring8() {
        //fail("Not yet implemented");
        mycustomstring.setString("01234567891111210");
        mycustomstring.convertDigitsToProductsInSubstring(2, 16);
        assertEquals("07257600", mycustomstring.getString());
    }

    //Test if the testConvertDigitsToProductsInSubstring method works when at least one of the products is 0
    @Test
    public void testConvertDigitsToProductsInSubstring9() {
        //fail("Not yet implemented");
        mycustomstring.setString(" 01234!! A 50789 @@ ' sdad 041120");
        mycustomstring.convertDigitsToProductsInSubstring(1, 20);
        assertEquals(" 0!! A 0 @@ ' sdad 041120", mycustomstring.getString());
    }

    //Test if the testConvertDigitsToProductsInSubstring method works on a large string
    @Test
    public void testConvertDigitsToProductsInSubstring10() {
        //fail("Not yet implemented");
        mycustomstring.setString("AA 112 '' 12340 .... 90003 qw 999 asdf 098 234!234 9000 as+++ !!--1@as asdf 111117 assdd 111221 77 19 0");
        mycustomstring.convertDigitsToProductsInSubstring(3, 100);
        assertEquals("AA 2 '' 0 .... 0 qw 729 asdf 0 24!24 0 as+++ !!--1@as asdf 7 assdd 4 49 19 0", mycustomstring.getString());
    }

}
