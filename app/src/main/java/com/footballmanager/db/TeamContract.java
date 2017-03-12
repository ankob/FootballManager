package com.footballmanager.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Team;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 01-Mar-17.
 */

public final class TeamContract {

    public static class Team implements BaseColumns {
        public static final String TABLE_NAME = "teams";
        public static final String NAME = "name";

        public static final String[] projection = { TABLE_NAME + "." + _ID, NAME };
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + Team.TABLE_NAME + " ("
            + Team._ID + " INTEGER PRIMARY KEY,"
            + Team.NAME + " TEXT" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Team.TABLE_NAME;

    public static void saveTeam(String name, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Team.NAME, name);
        db.insert(Team.TABLE_NAME, null, cv);
    }

    public static void updateTeam(long id, String name, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Team.NAME, name);
        String[] whereArgs = { Long.toString(id) };
        db.update(
                Team.TABLE_NAME,
                cv,
                Team._ID + " = ?",
                whereArgs
        );
    }

    public static List<MappedItem> getTeams(SQLiteDatabase db) {
        List<MappedItem> result = new LinkedList<>();
        Cursor cursor = db.query(
                Team.TABLE_NAME,
                Team.projection,
                "",
                null,
                "",
                "",
                ""
        );
        while (cursor.moveToNext())
            result.add(com.footballmanager.model.Team.makeInstance(cursor, db));
        return result;
    }
}
