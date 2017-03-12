package com.footballmanager.db;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 01-Mar-17.
 */

public final class RelMatchTeamContract {

    public static class RelMatchTeam implements BaseColumns {
        public static final String TABLE_NAME = "rel_match_team";
        public static final String TEAM = "team";
        public static final String MATCH = "match";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + RelMatchTeam.TABLE_NAME + " ("
            + RelMatchTeam._ID + " INTEGER PRIMARY KEY,"
            + RelMatchTeam.TEAM + " INTEGER,"
            + RelMatchTeam.MATCH + " INTEGER,"
            + " FOREIGN KEY (" + RelMatchTeam.TEAM + ") REFERENCES " + TeamContract.Team.TABLE_NAME + "(" + TeamContract.Team._ID + "),"
            + " FOREIGN KEY (" + RelMatchTeam.MATCH + ") REFERENCES " + MatchContract.Match.TABLE_NAME + "(" + MatchContract.Match._ID + ")" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + RelMatchTeam.TABLE_NAME;
}
