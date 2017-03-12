package com.footballmanager.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.RoleContract;

/**
 * Created by Andrew on 23-Feb-17.
 */

public class Role extends MappedItem {
    private String name;
    private long id;

    public Role(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role makeInstance(Cursor cursor, SQLiteDatabase db) {
        return new Role(
                cursor.getLong(cursor.getColumnIndex(RoleContract.Role._ID)),
                cursor.getString(cursor.getColumnIndex(RoleContract.Role.NAME))
        );
    }
}
