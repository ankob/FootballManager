package com.footballmanager.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;

import com.footballmanager.model.Role;
import com.footballmanager.model.User;

/**
 * Created by Andrew on 01-Feb-17.
 */

public final class UserContract {
    private UserContract() {}
    static MessageDigest messageDigest;

    public static class User implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String NAME = "name";
        public static final String HASH = "hash";
        public static final String ROLE = "role_id";

        public static final String[] projection = { TABLE_NAME + "." + _ID, NAME, ROLE };
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + User.TABLE_NAME + " ("
                + User._ID + " INTEGER PRIMARY KEY,"
                + User.NAME + " TEXT, "
                + User.HASH + " TEXT, "
                + User.ROLE + " INTEGER, "
                + " FOREIGN KEY (" + User.ROLE + ") REFERENCES " + RoleContract.Role.TABLE_NAME + "(" + RoleContract.Role._ID + ")" +
            ");";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + User.TABLE_NAME;

    public static void addNewUser(String name, String pass, Role role, SQLiteDatabase db) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(User.NAME, name);
            cv.put(User.HASH, calcHash(name, pass));
            cv.put(User.ROLE, role.getId());
            db.insert(User.TABLE_NAME, null, cv);;
        } catch (Exception e) {
            return;
        }
    }

    public static com.footballmanager.model.User getUser(String name, String pass, SQLiteDatabase db) {
        com.footballmanager.model.User result = null;
        try {
            String selection = User.NAME + " = ? AND " + User.HASH + " = ?";
            String[] selectionArgs = {name, calcHash(name, pass)};
            Cursor cursor = db.query(
                    User.TABLE_NAME,
                    User.projection,
                    selection,
                    selectionArgs,
                    "",
                    "",
                    ""
            );
            if (cursor.moveToNext()) {
                String userName = cursor.getString(cursor.getColumnIndex(User.NAME));
                long userId = cursor.getLong(cursor.getColumnIndex(User.TABLE_NAME + "." + User._ID));
                long roleId = cursor.getLong(cursor.getColumnIndex(User.ROLE));
                com.footballmanager.model.Role userRole = null;
                for (com.footballmanager.model.Role role: RoleContract.getRoles(db)) {
                    if (role.getId() == roleId)
                         userRole = role;
                }
                result = new com.footballmanager.model.User(userId, userName, userRole);
            }
        } catch (Exception e) {}
        return result;
    }

    public static String calcHash(String name, String pass) {
        try {
            if (messageDigest == null)
                messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((pass + name + "secret").getBytes("UTF-8"));
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    public static void updateUser(long id, String name, String pass, Role role, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(User.ROLE, role.getId());
        cv.put(User.NAME, name);
        cv.put(User.HASH, calcHash(name, pass));
        String[] whereArgs = { Long.toString(id) };
        db.update(
                User.TABLE_NAME,
                cv,
                User._ID + " = ?",
                whereArgs
        );
    }
}
