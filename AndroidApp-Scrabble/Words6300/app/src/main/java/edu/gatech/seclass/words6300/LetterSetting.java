package edu.gatech.seclass.words6300;

public class LetterSetting {
    private char letter;
    private int frequency;
    private int points;

    public LetterSetting(char letter, int frequency, int points) {
        setLetter(letter);
        setFrequency(frequency);
        setPoints(points);
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        if (!Character.isAlphabetic(letter)) {
            throw new IllegalArgumentException("Parameter letter must be an alphabetic letter.");
        }
        this.letter = letter;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        if (frequency < 0) {
            throw new IllegalArgumentException("Frequency must be >= 0");
        }
        this.frequency = frequency;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points must be >= 0");
        }
        this.points = points;
    }
}
