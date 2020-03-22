package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static List<Character> convertStringToCharList(String s) {
        List<Character> chars = new ArrayList<>();

        for (char ch : s.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }
}
