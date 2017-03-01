package com.footballmanager.db;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 01-Mar-17.
 */

public final class TeamContract {

    public static class Team implements BaseColumns {
        public static final String TABLE_NAME = "teams";
        public static final String NAME = "name";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + Team.TABLE_NAME + " ("
            + Team._ID + " INTEGER PRIMARY KEY,"
            + Team.NAME + " TEXT" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Team.TABLE_NAME;

}
