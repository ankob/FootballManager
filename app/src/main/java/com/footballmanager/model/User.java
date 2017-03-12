package com.footballmanager.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.RoleContract;
import com.footballmanager.db.UserContract;

/**
 * Created by Andrew on 25-Feb-17.
 */

public class User extends MappedItem {
    private long id;
    private String name;
    private Role role;

    static User instance;

    public static User getInstance() {
        return instance;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public User(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public long getId() {
        return id;
    }

    public static User makeInstance(Cursor cursor, SQLiteDatabase db) {
        long userId = cursor.getLong(cursor.getColumnIndex(UserContract.User._ID));

        User user = new User(
                userId,
                cursor.getString(cursor.getColumnIndex(UserContract.User.NAME)),
                null
        );
        String[] whereArgs = { Long.toString(cursor.getLong(cursor.getColumnIndex(UserContract.User.ROLE))) };
        Cursor roleCursor = db.query(
                RoleContract.Role.TABLE_NAME,
                RoleContract.Role.projection,
                RoleContract.Role._ID + " = ?",
                whereArgs,
                "",
                "",
                ""
        );
        if (roleCursor.moveToNext()) {
            user.role = (Role) Role.makeInstance(roleCursor, db);
        }
        return user;
    }
}
