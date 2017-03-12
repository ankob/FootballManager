package com.footballmanager.db;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 01-Mar-17.
 */

public final class MatchContract {

    public static class Match implements BaseColumns {
        public static final String TABLE_NAME = "matches";
        public static final String DATETIME = "datetime";
        public static final String DURATION = "duration";
        public static final String STADIUM = "stadium";
        public static final String RESULT = "result";

        public static final String[] projection = { _ID, DATETIME, DURATION, RESULT };
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + Match.TABLE_NAME + " ("
            + Match._ID + " INTEGER PRIMARY KEY,"
            + Match.DATETIME + " TEXT,"
            + Match.DURATION + " INTEGER,"
            + Match.STADIUM + " INTEGER,"
            + Match.RESULT + " TEXT,"
            + " FOREIGN KEY (" + Match.STADIUM + ") REFERENCES " + StadiumContract.Stadium.TABLE_NAME + " (" + StadiumContract.Stadium._ID + ") " +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Match.TABLE_NAME;

}
