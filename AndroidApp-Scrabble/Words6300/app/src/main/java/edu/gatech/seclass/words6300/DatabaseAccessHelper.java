package edu.gatech.seclass.words6300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DatabaseAccessHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "words6300.db";
    private static DatabaseAccessHelper instance;
    private final String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final String lineBreak = "\n";

    private DatabaseAccessHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized DatabaseAccessHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        DatabaseSetUpHelper setUpHelper = new DatabaseSetUpHelper();
        setUpHelper.createSettingsTable(sqLiteDatabase);
        setUpHelper.createGameStatusTable(sqLiteDatabase);
        setUpHelper.createLetterStatTable(sqLiteDatabase);
        setUpHelper.initializeLetterStatIfEmpty(sqLiteDatabase);
        setUpHelper.createWordBankTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    /*
     * Use this method to adjust max turns
     *
     */
    public String updateMaxTurnSettings(int newMaxTurn) {
        SQLiteDatabase db = this.getWritableDatabase();

        DatabaseSetUpHelper setUpHelper = new DatabaseSetUpHelper();
        int gameId = setUpHelper.initializeDefaultGameSettingValues(db);
        ContentValues contentValues = new ContentValues();
        contentValues.put("MAX_TURNS", newMaxTurn);
        long numUpdated = db.update("GAME_SETTINGS", contentValues, "game_id = " + gameId, null);
        db.close();
        if (numUpdated > 0) {
            return "Successfully Updated for the next game";
        }
        return "failed!";
    }


    /*
     * Use this method to adjust letter setting
     */
    public String updateLetterSettings(String letter, int distribution, int points) {
        SQLiteDatabase db = this.getWritableDatabase();

        DatabaseSetUpHelper setUpHelper = new DatabaseSetUpHelper();
        int gameId = setUpHelper.initializeDefaultGameSettingValues(db);
        ContentValues contentValues = new ContentValues();
        contentValues.put(letter + "_POINTS", points);
        contentValues.put(letter + "_DISTRIBUTION", distribution);

        long numUpdated = db.update("GAME_SETTINGS", contentValues, "game_id = " + gameId, null);

        db.close();
        if (numUpdated > 0) {
            return "Successfully Updated for the next game";
        }
        return "failed!";
    }

    //get new game settings
    public Cursor getSettingsDataForNewGame() {
        DatabaseSetUpHelper setUpHelper = new DatabaseSetUpHelper();
        SQLiteDatabase db = this.getWritableDatabase();

        int gameId = setUpHelper.initializeDefaultGameSettingValues(db);
        Cursor response = db.rawQuery("SELECT * FROM GAME_SETTINGS where GAME_SETTINGS.GAME_ID =  " + gameId, null);

        return response;
    }

    //get in progress game settings
    public Cursor getSettingsDataForInProgressGame() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM GAME_SETTINGS WHERE GAME_SETTINGS.GAME_ID =  (Select GAME_ID from game_status where In_PROGRESS = 1)", null);
        db.close();
        return response;
    }

    //get word bank
    public Cursor getWordBank() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor response = db.rawQuery("SELECT WORD, COUNT(*), MAX(DATETIME)  FROM WORD_BANK GROUP BY WORD ORDER BY MAX(DATETIME) DESC", null);
        return response;
    }

    //get game stats
    public Cursor getGameStatistics() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor response = db.rawQuery("SELECT SCORE, TURNS_COUNT, round((SCORE*1.0/ TURNS_COUNT) ,2) AS AVERAGE_SCORE,game_ID FROM GAME_STATUS ORDER BY SCORE DESC", null);
        return response;
    }

    //get game setting for a specific game
    public Cursor getGameSetting(int gameID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM GAME_SETTINGS where GAME_SETTINGS.GAME_ID =  " + gameID, null);
        return response;
    }

    //get letter stats
    public Cursor getLetterStats() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor response = db.rawQuery("SELECT LETTER, PLAYED, TRADED, round((PLAYED*1.0/DRAWN*100) ,2) AS PERCENT, DRAWN FROM LETTER_STATISTICS ORDER BY PLAYED ASC", null);
        return response;
    }


    //get in progress game status - this will give board, rack, pool, remaining turns, current score, etc
    public Cursor getGameInProgressStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM GAME_STATUS WHERE IN_PROGRESS='1'", null);
        return response;
    }

    //this method returns the game status and setting for new/inprogress game.
    public Game getGame() {
        Cursor gameStatus = getGameInProgressStatus();
        if (gameStatus.getCount() > 0) {
            if (!gameStatus.isFirst()) {
                gameStatus.moveToFirst();
            }
            int gameID = gameStatus.getInt(0);
            Cursor gameSetting = getGameSetting(gameID);
            GameSettings gameSettings = buildGameSetting(gameSetting);
            int score = gameStatus.getInt(1);
            int turnCount = gameStatus.getInt(2);
            LetterPool pool = new LetterPool(StringUtil.convertStringToCharList(gameStatus.getString(4)));
            Board board = new Board(StringUtil.convertStringToCharList(gameStatus.getString(3)));
            Rack rack = new Rack(StringUtil.convertStringToCharList(gameStatus.getString(5)));
            List<String> wordBank = getWordBankListForGame(gameID);
            Game game = new Game(gameID, gameSettings, wordBank, pool, board, rack, score, turnCount);
            return game;
        } else {
            Cursor gameSetting = getSettingsDataForNewGame();
            GameSettings gameSettings = buildGameSetting(gameSetting);
            Game game = new Game(gameSetting.getInt(0), gameSettings);
            // Update initial draw count for rack and board
            for (Character c : game.getRackLetters()) {
                LetterStatistics stat = getLetterStatistics(c);
                stat.addDraw();
                updateLetterStatistic(stat);
            }

            for (Character c : game.getBoardLetters()) {
                LetterStatistics stat = getLetterStatistics(c);
                stat.addDraw();
                updateLetterStatistic(stat);
            }

            return game;
        }

    }

    private GameSettings buildGameSetting(Cursor gameSetting) {
        List<LetterSetting> letterSettings = new ArrayList<LetterSetting>();

        if (!gameSetting.isFirst()) {
            gameSetting.moveToFirst();
        }

        for (String s : alphabets) {
            char c = s.charAt(0);
            int freq = gameSetting.getInt(gameSetting.getColumnIndex(s + "_DISTRIBUTION"));
            int point = gameSetting.getInt(gameSetting.getColumnIndex(s + "_POINTS"));
            LetterSetting letterSetting = new LetterSetting(c, freq, point);
            letterSettings.add(letterSetting);
        }
        GameSettings gameSettings = new GameSettings(gameSetting.getInt(gameSetting.getColumnIndex("MAX_TURNS")), letterSettings);
        return gameSettings;
    }

    /**
     * This is the main method which saves the game progress
     *
     * @param rack       - pass the first three params in a comma separated or single string. In whichever way that needs to be returned
     * @param score
     * @param gameID     - the current game id
     * @param pool
     * @param board
     * @param turnCount
     * @param inProgress = pass this value as 1 or 0 : 1 - true ; 0 - false
     * @return String - passed or failed.
     */
    public String saveGameProgress(int gameID, String rack, String pool, int score, String board, int turnCount, int inProgress) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor response = db.rawQuery("SELECT * FROM game_status where game_id=" + gameID, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("SCORE", score);
        contentValues.put("TURNS_COUNT", turnCount);
        contentValues.put("BOARD", board);
        contentValues.put("POOL", pool);
        contentValues.put("RACK", rack);
        contentValues.put("IN_PROGRESS", inProgress);

        //insert if a new data
        if (response.getCount() == 0) {
            contentValues.put("GAME_ID", gameID);
            long res = db.insert("GAME_STATUS", null, contentValues);
            db.close();
            if (res == -1) {
                return "failed";
            } else
                return "successfully inserted";
        } else {
            //update the existing
            long numUpdated = db.update("GAME_STATUS", contentValues, "game_id = " + gameID, null);
            db.close();
            if (numUpdated > 0) {
                return "Successfully Updated";
            }
            return "failed!";
        }
    }

    /**
     * This is the main method which saves the game progress
     */
    public String insertWordBank(List<WordBank> wordBankList) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = -1;
        for (WordBank wordBank : wordBankList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("WORD", wordBank.getWord());
            contentValues.put("GAME_ID", wordBank.getGameId());
            contentValues.put("DateTime", wordBank.getTime());

            res = db.insert("WORD_BANK", null, contentValues);
            db.close();
        }
        if (res == -1) {
            return "failed";
        } else
            return "successfully inserted";
    }

    public void updateWordBankWithWord(int gameId, String word, long dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("GAME_ID", gameId);
        values.put("WORD", word);
        values.put("DATETIME", dateTime);

        db.insert("WORD_BANK", null, values);
    }

    //get all words played in this game
    public List<String> getWordBankListForGame(int gameId) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> wordList = new ArrayList<>();
        Cursor response = db.rawQuery("SELECT WORD FROM WORD_BANK where GAME_ID =  " + gameId, null);
        if (response.getCount() > 0) {
            if (!response.isFirst()) {
                response.moveToFirst();
            }
            while (response.moveToNext()) {
                wordList.add(response.getString(0));
            }
        }
        return wordList;

    }

    //update letter stats
    public void updateLetterStatistics(List<LetterStatistics> letterStatisticsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (LetterStatistics letterStatistics : letterStatisticsList) {
            String query = "Update LETTER_STATISTICS set  Played = Played+" + letterStatistics.getPlayCount() + ", Traded = Traded+" + letterStatistics.getTradeCount() +
                    " , Drawn = Drawn+" + letterStatistics.getDrawCount() + " where LETTER = '" + letterStatistics.getLetter() + "'";
            Cursor c = db.rawQuery(query, null);

            c.moveToFirst();
            c.close();
        }
        db.close();


    }

    public void updateLetterStatistic(LetterStatistics letterStatistics) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PLAYED", letterStatistics.getPlayCount());
        values.put("TRADED", letterStatistics.getTradeCount());
        values.put("DRAWN", letterStatistics.getDrawCount());
        String[] whereArgs = new String[1];
        whereArgs[0] = "" + letterStatistics.getLetter();
        db.update("LETTER_STATISTICS", values, "LETTER=?", whereArgs);
        db.close();
    }

    public LetterStatistics getLetterStatistics(Character letter) {
        LetterStatistics stat = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT PLAYED, DRAWN, TRADED FROM LETTER_STATISTICS WHERE LETTER = '" + letter + "'", null);
        if (response.getCount() > 0) {
            response.moveToFirst();
            int played = response.getInt(0);
            int drawn = response.getInt(1);
            int traded = response.getInt(2);
            stat = new LetterStatistics(letter, played, drawn, traded);
        }
        response.close();
        db.close();
        if (stat == null) {
            stat = new LetterStatistics(letter);
        }
        return stat;
    }

    public List<String> getGameStatisticsAsList() {
        Cursor cursor = getGameStatistics();
        List<String> gameStatList = new LinkedList<String>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                gameStatList.add(cursor.getInt(0) + "," + cursor.getInt(1) + "," + cursor.getDouble(2) + "," + cursor.getInt(3));
            }
        } else {
            gameStatList.add("There is no data available to provide the statistics");
        }

        return gameStatList;
    }

    public String getMaxTurnAsString(int gameID) {
        Cursor gameSetting = getGameSetting(gameID);
        if (!gameSetting.isFirst()) {
            gameSetting.moveToFirst();
        }
        if (gameSetting.getCount() > 0) {
            return gameSetting.getInt(gameSetting.getColumnIndex("MAX_TURNS")) + "";
        } else {
            return "0";
        }
    }

    public String getLetterSettingAsString(int gameID) {
        StringBuffer settingAsString = new StringBuffer();
        Cursor gameSetting = getGameSetting(gameID);
        if (!gameSetting.isFirst()) {
            gameSetting.moveToFirst();
        }
        if (gameSetting.getCount() > 0) {
            for (String s : alphabets) {
                int freq = gameSetting.getInt(gameSetting.getColumnIndex(s + "_DISTRIBUTION"));
                int point = gameSetting.getInt(gameSetting.getColumnIndex(s + "_POINTS"));
                settingAsString.append(s + "," + point + "," + freq + lineBreak);
            }
        }
        return settingAsString.toString();
    }

    public LetterSetting getLetterSetting(Character c) {
        DatabaseSetUpHelper setUpHelper = new DatabaseSetUpHelper();
        SQLiteDatabase db = this.getWritableDatabase();
        int gameId = setUpHelper.initializeDefaultGameSettingValues(db);
        Cursor gameSetting = getGameSetting(gameId);
        if (!gameSetting.isFirst()) {
            gameSetting.moveToFirst();
        }
        if (gameSetting.getCount() > 0) {
            int freq = gameSetting.getInt(gameSetting.getColumnIndex(c + "_DISTRIBUTION"));
            int point = gameSetting.getInt(gameSetting.getColumnIndex(c + "_POINTS"));
            return new LetterSetting(c, freq, point);
        }
        return null;
    }

    public String getLetterStatsAsString() {
        Cursor cursor = getLetterStats();
        StringBuffer letterStats = new StringBuffer();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                letterStats.append(cursor.getString(0) + "," + cursor.getInt(1) + "," + cursor.getInt(2) + "," + cursor.getDouble(3) + lineBreak);
            }
            return letterStats.toString();
        }
        return "";
    }

    public Map<Character, LetterStatistics> getLetterStatisticsMap() {
        Cursor letterStatsCursor = getLetterStats();
        Map<Character, LetterStatistics> letterStatistics = new HashMap<>();
        if (letterStatsCursor.getCount() > 0) {
            while (letterStatsCursor.moveToNext()) {
                char letter = letterStatsCursor.getString(0).charAt(0);
                int playCount = letterStatsCursor.getInt(1);
                int tradeCount = letterStatsCursor.getInt(2);
                int drawCount = letterStatsCursor.getInt(4);
                LetterStatistics stat = new LetterStatistics(letter, playCount, drawCount, tradeCount);
                letterStatistics.put(letter, stat);
            }
        }
        return letterStatistics;
    }

    public String getWordBankAsString() {
        Cursor cursor = getWordBank();
        StringBuffer wordBank = new StringBuffer();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                wordBank.append(cursor.getString(0) + "," + cursor.getInt(1) + lineBreak);
            }
            return wordBank.toString();
        }
        return "There is no data to provide the results";
    }

    public List<Character> getAlphabet() {
        return new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
    }

}
