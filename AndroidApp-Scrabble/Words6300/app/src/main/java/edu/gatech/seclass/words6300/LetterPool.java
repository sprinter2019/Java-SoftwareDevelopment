package edu.gatech.seclass.words6300;

import java.util.List;
import java.util.Random;

public class LetterPool {
    private List<Character> pool;

    public LetterPool(List letters) {
        this.pool = letters;
    }

    public void addLetter(char letter) {
        pool.add(letter);
    }

    public Character getNewLetter() {
        if (pool.size() > 0) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(pool.size());
            return pool.remove(randomIndex);
        }
        return null;
    }

    public int getLetterCount() {
        return pool.size();
    }

    public List<Character> getPoolLetters() {
        return pool;
    }
}
