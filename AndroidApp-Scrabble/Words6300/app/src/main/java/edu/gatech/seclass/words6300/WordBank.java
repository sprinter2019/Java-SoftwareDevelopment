package edu.gatech.seclass.words6300;

import java.util.Date;

public class WordBank {
    private String word;
    private long time;
    private String gameId;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getTime() {
        return time;
    }

    public void setTime(Date date) {
        this.time = date.getTime();
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }


}
