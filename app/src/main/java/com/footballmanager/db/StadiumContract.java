package com.footballmanager.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Andrew on 01-Mar-17.
 */

public final class StadiumContract {

    public static class Stadium implements BaseColumns {
        public static final String TABLE_NAME = "stadiums";
        public static final String NAME = "name";
        public static final String FIELDS = "fields";

        public static final String[] projection = { _ID, NAME, FIELDS };
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + Stadium.TABLE_NAME + " ("
            + Stadium._ID + " INTEGER PRIMARY KEY,"
            + Stadium.NAME + " TEXT,"
            + Stadium.FIELDS + " INTEGER" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Stadium.TABLE_NAME;

    public static void saveStadium(String name, int fields, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Stadium.NAME, name);
        db.insert(
                Stadium.TABLE_NAME,
                null,
                cv
        );
    }

    public static void updateStadium(long id, String name, int fields, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Stadium.NAME, name);
        String[] whereArgs = { Long.toString(id) };
        db.update(
                Stadium.TABLE_NAME,
                cv,
                Stadium._ID + " = ?",
                whereArgs
        );
    }
}
