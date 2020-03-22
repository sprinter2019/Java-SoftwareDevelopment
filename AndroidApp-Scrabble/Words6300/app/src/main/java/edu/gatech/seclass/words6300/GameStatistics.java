package edu.gatech.seclass.words6300;

public class GameStatistics {
    private int finalScore;
    private int numberOfTurns;
    private double averageScore;
    private GameSettings gameSettings;

    public GameStatistics(int finalScore, int numberOfTurns, GameSettings stats) {
        this.finalScore = finalScore;
        this.numberOfTurns = numberOfTurns;
        this.averageScore = ((double) finalScore) / numberOfTurns;
        this.gameSettings = stats;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    private double getAverageScorePerTurn() {
        return averageScore;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }
}
