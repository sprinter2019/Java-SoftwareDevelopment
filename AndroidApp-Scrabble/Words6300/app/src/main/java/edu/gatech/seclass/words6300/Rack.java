package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private List<Character> letters;
    private int capacity = 7;

    public Rack(int capacity) {
        letters = new ArrayList<>();
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.capacity = capacity;
    }

    public Rack(List<Character> letters) {
        this.letters = letters;
    }

    public List<Character> getLetters() {
        return letters;
    }

    public boolean hasExtraCapacity() {
        return letters.size() < capacity;
    }

    public void removeLetters(List<Character> lettersToRemove) {
        if (!letters.containsAll(lettersToRemove)) {
            throw new IllegalArgumentException("Not all letters are on the board.");
        }
        for (Character c : lettersToRemove) {
            letters.remove(c);
        }
    }

    public void addLetter(char letter) {
        if (!hasExtraCapacity()) {
            throw new IllegalStateException("The rack is full.  Letters cannot be added.");
        }
        letters.add(letter);
    }
}
