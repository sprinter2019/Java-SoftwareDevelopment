package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.List;

public class LetterUtilities {
    public static List<Character> GetAlphabetAsList() {
        // Inspired by this StackOverflow answer by user Hunter McMillen:
        // https://stackoverflow.com/a/17575926/6217795
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        List<Character> cList = getCharacterListFromArray(alphabet);
        return cList;
    }

    public static List<Character> getCharacterListFromArray(char[] alphabet) {
        //https://coderanch.com/t/534080/java/Arrays-asList-character-array
        List<Character> cList = new ArrayList<>();
        for (char ch : alphabet) {
            cList.add(ch);
        }
        return cList;
    }

    public static String getStringFromCharacterList(List<Character> charList) {
        StringBuilder builder = new StringBuilder();
        for (Character c : charList) {
            builder.append(c);
        }
        return builder.toString();
    }
}
