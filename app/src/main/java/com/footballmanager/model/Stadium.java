package com.footballmanager.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.StadiumContract;

/**
 * Created by Andrew on 07-Mar-17.
 */

public class Stadium extends MappedItem {
    private long id;
    private String name;
    private int fields;

    public Stadium(long id, String name, int fields) {
        this.id = id;
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public int getFields() {
        return fields;
    }

    @Override
    public long getId() {
        return id;
    }

    public static Stadium makeInstance(Cursor cursor, SQLiteDatabase db) {
        return new Stadium(
                cursor.getLong(cursor.getColumnIndex(StadiumContract.Stadium._ID)),
                cursor.getString(cursor.getColumnIndex(StadiumContract.Stadium.NAME)),
                cursor.getInt(cursor.getColumnIndex(StadiumContract.Stadium.FIELDS))
        );
    }
}
