package com.footballmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Andrew on 02-Feb-17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Warships.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.SQL_CREATE_ENTRIES);
        db.execSQL(RoleContract.SQL_CREATE_ENTRIES);

        // Create roles

        // Manager role to edit
        ContentValues cv = new ContentValues();
        cv.put(RoleContract.Role.NAME, "Manager");
        db.insert(RoleContract.Role.TABLE_NAME, null, cv);

        // Player role to view
        cv = new ContentValues();
        cv.put(RoleContract.Role.NAME, "Player");
        db.insert(RoleContract.Role.TABLE_NAME, null, cv);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.SQL_DELETE_ENTRIES);
        db.execSQL(RoleContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
