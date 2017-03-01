package com.footballmanager.db;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 01-Mar-17.
 */

public final class RelMatchPlayerContract {

    public static class RelMatchPlayer implements BaseColumns {
        public static final String TABLE_NAME = "rel_player_team";
        public static final String PLAYER = "player";
        public static final String MATCH = "match";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + RelMatchPlayer.TABLE_NAME + " ("
            + RelMatchPlayer._ID + " INTEGER PRIMARY KEY,"
            + RelMatchPlayer.PLAYER + " INTEGER,"
            + RelMatchPlayer.MATCH + " INTEGER"
            + " FOREIGN KEY (" + RelMatchPlayer.PLAYER + ") REFERENCES " + UserContract.User.TABLE_NAME + "(" + UserContract.User._ID + ")"
            + " FOREIGN KEY (" + RelMatchPlayer.MATCH + ") REFERENCES " + TeamContract.Team.TABLE_NAME + "(" + MatchContract.Match._ID + ")" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + RelMatchPlayer.TABLE_NAME;
}
