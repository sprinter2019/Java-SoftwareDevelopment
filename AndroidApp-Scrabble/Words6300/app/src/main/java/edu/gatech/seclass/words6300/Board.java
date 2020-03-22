package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Character> letters;
    private final int capacity = 4;

    public Board() {
        letters = new ArrayList<>();
    }

    public Board(List<Character> letters) {
        this.letters = letters;
    }

    public List<Character> getLetters() {
        return letters;
    }

    public void addLetter(char letter) {
        if (letters.size() == capacity) {
            throw new IllegalStateException("Board is full.");
        }
        letters.add(letter);
    }

    public void replaceLetter(char oldLetter, char newLetter) {
        if (!letters.contains(oldLetter)) {
            throw new IllegalStateException("Old letter is not currently on the board.");
        }
        if (oldLetter == newLetter) {
            return;
        }
        letters.remove((Character) oldLetter);
        letters.add(newLetter);
    }
}
