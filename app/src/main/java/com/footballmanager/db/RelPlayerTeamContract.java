package com.footballmanager.db;

/**
 * Created by Andrew on 01-Mar-17.
 */

import android.provider.BaseColumns;

public final class RelPlayerTeamContract {

    public static class RelPlayerTeam implements BaseColumns {
        public static final String TABLE_NAME = "rel_player_team";
        public static final String PLAYER = "player";
        public static final String TEAM = "team";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + RelPlayerTeam.TABLE_NAME + " ("
            + RelPlayerTeam._ID + " INTEGER PRIMARY KEY,"
            + RelPlayerTeam.PLAYER + " INTEGER,"
            + RelPlayerTeam.TEAM + " INTEGER,"
            + " FOREIGN KEY (" + RelPlayerTeam.PLAYER + ") REFERENCES " + UserContract.User.TABLE_NAME + "(" + UserContract.User._ID + "),"
            + " FOREIGN KEY (" + RelPlayerTeam.TEAM + ") REFERENCES " + TeamContract.Team.TABLE_NAME + "(" + TeamContract.Team._ID + ")" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + RelPlayerTeam.TABLE_NAME;

}
