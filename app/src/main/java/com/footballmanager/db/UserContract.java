package com.footballmanager.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import java.security.MessageDigest;

import com.footballmanager.model.Role;
import com.footballmanager.model.User;

/**
 * Created by Andrew on 01-Feb-17.
 */

public final class UserContract {
    private UserContract() {}

    public static class User implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String NAME = "name";
        public static final String HASH = "hash";
        public static final String ROLE = "role_id";
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

    public static com.footballmanager.model.User addNewUser(String name, String pass, Role role, SQLiteDatabase db) {
        com.footballmanager.model.User newUser = new com.footballmanager.model.User(name, role);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            ContentValues cv = new ContentValues();
            cv.put(User.NAME, newUser.getName());
            cv.put(User.HASH, messageDigest.digest((newUser.getName() + pass).getBytes()));
            cv.put(User.ROLE, role.getId());
            db.insert(User.TABLE_NAME, null, cv);
            return newUser;
        } catch (Exception e) {
            return null;
        }
    }

    public static com.footballmanager.model.User getUser(String name, String pass, SQLiteDatabase db) {
        com.footballmanager.model.User result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String[] columns = {User.NAME, User.ROLE};
            String selection = User.NAME + " = ? AND " + User.HASH + " = ?";
            String[] selectionArgs = {name, messageDigest.digest((name + pass).getBytes()).toString()};
            Cursor cursor = db.query(
                    User.TABLE_NAME,
                    columns,
                    selection,
                    selectionArgs,
                    "",
                    "",
                    ""
            );
            if (cursor.moveToNext()) {
                String userName = cursor.getString(cursor.getColumnIndex(User.NAME));
                long roleId = cursor.getLong(cursor.getColumnIndex(User.ROLE));
                com.footballmanager.model.Role userRole = null;
                for (com.footballmanager.model.Role role: RoleContract.getRoles(db)) {
                    if (role.getId() == roleId)
                         userRole = role;
                }
                result = new com.footballmanager.model.User(userName, userRole);
            }
        } catch (Exception e) {}
        return result;
    }
}
