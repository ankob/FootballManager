package com.footballmanager.db;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 23-Feb-17.
 */

public final class RoleContract {
    private RoleContract() {}

    public static class Role implements BaseColumns {
        public static final String TABLE_NAME = "roles";
        public static final String NAME = "name";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
                + Role.TABLE_NAME + " ("
                + Role._ID + " INTEGER PRIMARY KEY,"
                + Role.NAME + " TEXT" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Role.TABLE_NAME;
}
