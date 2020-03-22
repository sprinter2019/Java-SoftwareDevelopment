package edu.gatech.seclass.words6300;

public class LetterStatistics {
    private char letter;
    private int playCount;
    private int drawCount;
    private int tradeCount;

    public LetterStatistics(char letter, int playCount, int drawCount, int tradeCount) {
        this.letter = letter;
        this.playCount = playCount;
        this.drawCount = drawCount;
        this.tradeCount = tradeCount;
    }

    public LetterStatistics(char letter) {
        this.letter = letter;
        playCount = 0;
        drawCount = 0;
        tradeCount = 0;
    }

    public char getLetter() {
        return letter;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void addPlay() {
        playCount++;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void addDraw() {
        drawCount++;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void addTrade() {
        tradeCount++;
    }

    public double getPercentagePlayed() {
        return drawCount == 0 ? 0 : ((double) playCount) / drawCount;
    }
}
