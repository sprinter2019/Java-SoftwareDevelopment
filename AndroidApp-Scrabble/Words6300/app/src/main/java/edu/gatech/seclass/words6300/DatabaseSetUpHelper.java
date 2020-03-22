package edu.gatech.seclass.words6300;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSetUpHelper   {




    public void createLetterStatTable(SQLiteDatabase sqLiteDatabase) {
        String createLetterStatisticsQuery = "CREATE TABLE IF NOT EXISTS LETTER_STATISTICS (LETTER TEXT,  PLAYED INTEGER, TRADED INTEGER, DRAWN INTEGER)" ;
        sqLiteDatabase.execSQL(createLetterStatisticsQuery);
    }

    public void createWordBankTable(SQLiteDatabase sqLiteDatabase) {
        String createWordStatisticsQuery = "CREATE TABLE IF NOT EXISTS WORD_BANK (WORD TEXT, GAME_ID INTEGER, DATETIME int)";
        sqLiteDatabase.execSQL(createWordStatisticsQuery);

    }

    public void createGameStatusTable(SQLiteDatabase sqLiteDatabase) {
        String createGameStatusQuery = "CREATE TABLE IF NOT EXISTS GAME_STATUS (GAME_ID integer , SCORE integer , TURNS_COUNT INTEGER, BOARD TEXT, POOL TEXT, RACK TEXT, IN_PROGRESS INT )" ;
        sqLiteDatabase.execSQL(createGameStatusQuery);

    }

    public void createSettingsTable(SQLiteDatabase sqLiteDatabase) {
        String createSettingsQuery = "CREATE TABLE IF NOT EXISTS GAME_SETTINGS (GAME_ID integer primary key autoincrement, MAX_TURNS INTEGER, " +
                "A_POINTS INTEGER , A_DISTRIBUTION INTEGER ," +
                "B_POINTS INTEGER , B_DISTRIBUTION INTEGER ," +
                "C_POINTS INTEGER , C_DISTRIBUTION INTEGER ," +
                "D_POINTS INTEGER , D_DISTRIBUTION INTEGER ," +
                "E_POINTS INTEGER , E_DISTRIBUTION INTEGER ," +
                "F_POINTS INTEGER , F_DISTRIBUTION INTEGER ," +
                "G_POINTS INTEGER , G_DISTRIBUTION INTEGER ," +
                "H_POINTS INTEGER , H_DISTRIBUTION INTEGER ," +
                "I_POINTS INTEGER , I_DISTRIBUTION INTEGER ," +
                "J_POINTS INTEGER , J_DISTRIBUTION INTEGER ," +
                "K_POINTS INTEGER , K_DISTRIBUTION INTEGER ," +
                "L_POINTS INTEGER , L_DISTRIBUTION INTEGER ," +
                "M_POINTS INTEGER , M_DISTRIBUTION INTEGER ," +
                "N_POINTS INTEGER , N_DISTRIBUTION INTEGER ," +
                "O_POINTS INTEGER , O_DISTRIBUTION INTEGER ," +
                "P_POINTS INTEGER , P_DISTRIBUTION INTEGER ," +
                "Q_POINTS INTEGER , Q_DISTRIBUTION INTEGER ," +
                "R_POINTS INTEGER , R_DISTRIBUTION INTEGER ," +
                "S_POINTS INTEGER , S_DISTRIBUTION INTEGER ," +
                "T_POINTS INTEGER , T_DISTRIBUTION INTEGER ," +
                "U_POINTS INTEGER , U_DISTRIBUTION INTEGER ," +
                "V_POINTS INTEGER , V_DISTRIBUTION INTEGER ," +
                "W_POINTS INTEGER , W_DISTRIBUTION INTEGER ," +
                "X_POINTS INTEGER , X_DISTRIBUTION INTEGER ," +
                "Y_POINTS INTEGER , Y_DISTRIBUTION INTEGER ," +
                "Z_POINTS INTEGER , Z_DISTRIBUTION INTEGER  )";

        sqLiteDatabase.execSQL(createSettingsQuery);
    }




    /*
     * Use this method when the number of rows in the game setting is same as the number of rows
     * in the game status and a new game has to be started or a setting adjustment has to be made
     *
     */
    public int initializeDefaultGameSettingValues(SQLiteDatabase db){
        Cursor gameSettingsId = db.rawQuery("SELECT count(*), max(game_id) FROM GAME_SETTINGS", null);
        Cursor gameStatusId = db.rawQuery("SELECT count(*), max(game_id) FROM GAME_STATUS",null);

        if (gameSettingsId.moveToFirst() && gameStatusId.moveToFirst()) {
            if (gameSettingsId.getInt(0) != 0 && gameSettingsId.getInt(1) != gameStatusId.getInt(1)) {
                return gameSettingsId.getInt(1);
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("MAX_TURNS", 10);
        contentValues.put("A_DISTRIBUTION", 9);
        contentValues.put("B_DISTRIBUTION", 2);
        contentValues.put("C_DISTRIBUTION", 2);
        contentValues.put("D_DISTRIBUTION", 4);
        contentValues.put("E_DISTRIBUTION", 12);
        contentValues.put("F_DISTRIBUTION", 2);
        contentValues.put("G_DISTRIBUTION", 3);
        contentValues.put("H_DISTRIBUTION", 2);
        contentValues.put("I_DISTRIBUTION", 9);
        contentValues.put("J_DISTRIBUTION", 1);
        contentValues.put("K_DISTRIBUTION", 1);
        contentValues.put("L_DISTRIBUTION", 4);
        contentValues.put("M_DISTRIBUTION", 2);
        contentValues.put("N_DISTRIBUTION", 6);
        contentValues.put("O_DISTRIBUTION", 8);
        contentValues.put("P_DISTRIBUTION", 2);
        contentValues.put("Q_DISTRIBUTION", 1);
        contentValues.put("R_DISTRIBUTION", 6);
        contentValues.put("S_DISTRIBUTION", 4);
        contentValues.put("T_DISTRIBUTION", 6);
        contentValues.put("U_DISTRIBUTION", 4);
        contentValues.put("V_DISTRIBUTION", 2);
        contentValues.put("W_DISTRIBUTION", 2);
        contentValues.put("X_DISTRIBUTION", 1);
        contentValues.put("Y_DISTRIBUTION", 2);
        contentValues.put("Z_DISTRIBUTION", 1);
        contentValues.put("A_POINTS", 1);
        contentValues.put("B_POINTS", 3);
        contentValues.put("C_POINTS", 3);
        contentValues.put("D_POINTS", 2);
        contentValues.put("E_POINTS", 1);
        contentValues.put("F_POINTS", 4);
        contentValues.put("G_POINTS", 2);
        contentValues.put("H_POINTS", 4);
        contentValues.put("I_POINTS", 1);
        contentValues.put("J_POINTS", 8);
        contentValues.put("K_POINTS", 5);
        contentValues.put("L_POINTS", 1);
        contentValues.put("M_POINTS", 3);
        contentValues.put("N_POINTS", 1);
        contentValues.put("O_POINTS", 1);
        contentValues.put("P_POINTS", 3);
        contentValues.put("Q_POINTS", 10);
        contentValues.put("R_POINTS", 1);
        contentValues.put("S_POINTS", 1);
        contentValues.put("T_POINTS", 1);
        contentValues.put("U_POINTS", 1);
        contentValues.put("V_POINTS", 4);
        contentValues.put("W_POINTS", 4);
        contentValues.put("X_POINTS", 8);
        contentValues.put("Y_POINTS", 4);
        contentValues.put("Z_POINTS", 10);

        long response = db.insert("GAME_SETTINGS", null,  contentValues);
        return (int) response;
    }
    public void initializeLetterStatIfEmpty(SQLiteDatabase db1) {


        Cursor response = db1.rawQuery("SELECT * FROM LETTER_STATISTICS", null) ;
        if(response.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
                    "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            for(String letter: alphabets) {
                contentValues.put("LETTER",letter );
                contentValues.put("PLAYED", 0);
                contentValues.put("TRADED", 0);
                contentValues.put("DRAWN", 0);

                long resp = db1.insert("LETTER_STATISTICS", null, contentValues);
            }


        }

    }

}