package com.footballmanager.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.RelPlayerTeamContract;
import com.footballmanager.db.TeamContract;
import com.footballmanager.db.UserContract;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 09-Mar-17.
 */

public class Team extends MappedItem {
    private long id;
    private String name;
    private List<User> players;

    public Team(long id, String name, List<User> players) {
        this.name = name;
        this.id = id;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public List<User> getPlayers() {
        return players;
    }

    public static Team makeInstance(Cursor cursor, SQLiteDatabase db) {
        long teamId = cursor.getLong(cursor.getColumnIndex(TeamContract.Team._ID));
        String[] whereArgs = { Long.toString(teamId) };
        Cursor userCursor = db.query(
                UserContract.User.TABLE_NAME + " INNER JOIN " + RelPlayerTeamContract.RelPlayerTeam.TABLE_NAME,
                UserContract.User.projection,
                RelPlayerTeamContract.RelPlayerTeam.TEAM + " = ?",
                whereArgs,
                "",
                "",
                ""
        );
        LinkedList<User> players = new LinkedList<>();
        while (userCursor.moveToNext()) {
            players.add(User.makeInstance(userCursor, db));
        }
        return new Team(
                teamId,
                cursor.getString(cursor.getColumnIndex(TeamContract.Team.NAME)),
                players
        );
    }
}
