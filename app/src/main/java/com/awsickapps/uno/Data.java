package com.awsickapps.uno;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by allen on 3/19/15.
 */
public class Data {

    public static final String WINNING_SCREEN_KEY = "winner_key";
    public static final String WINNER_NAME_KEY = "winner_name_key";
    public static final String QUIT_KEY = "quit_key";
    public static final String RESTART_KEY = "restart_key";
    public static final int QUIT_REQUEST_CODE = 69;
    public static final int WIN_REQUEST_CODE = 68;
    public static final int QUIT_RESULT_CODE = 67;
    public static final int RESTART_RESULT_CODE = 66;
    public static final int SKIP = 10;
    public static final int REVERSE = 11;
    public static final int DRAW_TWO = 12;
    public static final int WILD = 13;
    public static final int WILD_DRAW_FOUR = 14;

    public static final String HIGH_SCORES_KEY = "high_scores_key";

    public static String getHighScores(Context context){
        SharedPreferences sp = context.getSharedPreferences(HIGH_SCORES_KEY, Context.MODE_PRIVATE);
        return sp.getString(HIGH_SCORES_KEY, "");
    }

    public static void addHighScore(Context context, int score){
        SharedPreferences sp = context.getSharedPreferences(HIGH_SCORES_KEY, Context.MODE_PRIVATE);
        String hs = sp.getString(HIGH_SCORES_KEY, "");
        if (hs.equals(""))
            sp.edit().putString(HIGH_SCORES_KEY, "" + score).apply();
        else
            sp.edit().putString(HIGH_SCORES_KEY, hs + "," + score).apply();

    }

}
