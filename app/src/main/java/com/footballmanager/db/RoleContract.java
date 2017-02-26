package com.footballmanager.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 23-Feb-17.
 */

public final class RoleContract {

    public static final long MANGER_ROLE_ID = 1L;

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

    private static List<com.footballmanager.model.Role> roles = new LinkedList<>();

    public static List<com.footballmanager.model.Role> getRoles(SQLiteDatabase db) {
        if (!roles.isEmpty())
            return roles;

        String[] columns = { Role.NAME, Role._ID };
        String[] selectArgs = {};
        Cursor cursor = db.query(
                Role.TABLE_NAME,
                columns,
                "",
                selectArgs,
                "",
                "",
                ""
        );
        while (cursor.moveToNext()) {
            roles.add(new com.footballmanager.model.Role(
                    cursor.getString(cursor.getColumnIndex(Role.NAME)),
                    cursor.getLong(cursor.getColumnIndex(Role._ID))
            ));
        }
        return roles;
    }
}
