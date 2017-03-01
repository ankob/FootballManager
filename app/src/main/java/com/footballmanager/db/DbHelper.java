package com.footballmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.footballmanager.model.Role;


/**
 * Created by Andrew on 02-Feb-17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Warships.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.SQL_CREATE_ENTRIES);
        db.execSQL(RoleContract.SQL_CREATE_ENTRIES);
        db.execSQL(StadiumContract.SQL_CREATE_ENTRIES);
        db.execSQL(MatchContract.SQL_CREATE_ENTRIES);
        db.execSQL(TeamContract.SQL_CREATE_ENTRIES);
        db.execSQL(RelPlayerTeamContract.SQL_CREATE_ENTRIES);
        db.execSQL(RelMatchPlayerContract.SQL_CREATE_ENTRIES);

        // Create roles

        // Manager role to edit
        ContentValues cv = new ContentValues();
        cv.put(RoleContract.Role.NAME, "Manager");
        cv.put(RoleContract.Role._ID, RoleContract.MANGER_ROLE_ID);
        db.insert(RoleContract.Role.TABLE_NAME, null, cv);

        // Player role to view
        cv = new ContentValues();
        cv.put(RoleContract.Role.NAME, "Player");
        db.insert(RoleContract.Role.TABLE_NAME, null, cv);

        // Create first admin user to all another.
        UserContract.addNewUser("admin", "shortpassword", new Role("Manager", RoleContract.MANGER_ROLE_ID), db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.SQL_DELETE_ENTRIES);
        db.execSQL(RoleContract.SQL_DELETE_ENTRIES);
        db.execSQL(StadiumContract.SQL_DELETE_ENTRIES);
        db.execSQL(MatchContract.SQL_DELETE_ENTRIES);
        db.execSQL(TeamContract.SQL_DELETE_ENTRIES);
        db.execSQL(RelPlayerTeamContract.SQL_DELETE_ENTRIES);
        db.execSQL(RelMatchPlayerContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
