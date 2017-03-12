package com.footballmanager.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.MatchContract;
import com.footballmanager.db.RelMatchTeamContract;
import com.footballmanager.db.RelPlayerTeamContract;
import com.footballmanager.db.StadiumContract;
import com.footballmanager.db.TeamContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 07-Mar-17.
 */

public class Match extends MappedItem {
    private long id;
    private Date datetime;
    private int duration;
    private Stadium stadium;
    private String result;
    private List<Team> teams;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    protected static final String teamsDelimiter = " vs ";

    public Match(long id, Date datetime, int duration, Stadium stadium, String result, List<Team> teams) {
        this.id = id;
        this.datetime = datetime;
        this.duration = duration;
        this.stadium = stadium;
        this.result = result;
        this.teams = teams;
    }

    public long getId() {
        return id;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Date getDatetime() {
        return datetime;
    }

    public int getDuration() {
        return duration;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String getName() {
        StringBuilder result = new StringBuilder(datetimeFormat.format(datetime) + " ");
        for(Team team: teams) {
            result.append(team.getName() + teamsDelimiter);
        }
        return result.substring(0, result.length() - teamsDelimiter.length());
    }

    public static Match makeInstance(Cursor cursor, SQLiteDatabase db) {
        try {
            long matchId = cursor.getLong(cursor.getColumnIndex(MatchContract.Match.TABLE_NAME + "." + MatchContract.Match._ID));
            String[] stadiumWhereArgs = { Long.toString(matchId) };
            Cursor stadiumCursor = db.query(
                    StadiumContract.Stadium.TABLE_NAME,
                    StadiumContract.Stadium.projection,
                    StadiumContract.Stadium._ID + " = ?",
                    stadiumWhereArgs,
                    "",
                    "",
                    ""
            );
            Stadium stadium = null;
            if (stadiumCursor.moveToNext()) {
                stadium = Stadium.makeInstance(stadiumCursor, db);
            }
            LinkedList<Team> teams = new LinkedList<>();

            String[] teamWhereArgs = { Long.toString(matchId) };
            Cursor teamsCursor = db.query(
                    TeamContract.Team.TABLE_NAME + " INNER JOIN " + RelMatchTeamContract.RelMatchTeam.TABLE_NAME,
                    TeamContract.Team.projection,
                    RelMatchTeamContract.RelMatchTeam.MATCH + " = ?",
                    teamWhereArgs,
                    "",
                    "",
                    ""
            );
            while (teamsCursor.moveToNext()) {
                teams.add(Team.makeInstance(teamsCursor, db));
            }

            return new Match(
                    matchId,
                    datetimeFormat.parse(cursor.getString(cursor.getColumnIndex(MatchContract.Match.DATETIME))),
                    cursor.getInt(cursor.getColumnIndex(MatchContract.Match.DURATION)),
                    stadium,
                    cursor.getString(cursor.getColumnIndex(MatchContract.Match.RESULT)),
                    teams
            );
        } catch (ParseException e) {
            return null;
        }
    }

    public static void saveMatch(
            String datetime,
            int duration,
            Stadium stadium,
            String result,
            Team team1,
            Team team2,
            SQLiteDatabase db
    ) {
        ContentValues cv = new ContentValues();
        cv.put(MatchContract.Match.DATETIME, datetime);
        cv.put(MatchContract.Match.DURATION, duration);
        cv.put(MatchContract.Match.RESULT, result);
        cv.put(MatchContract.Match.STADIUM, stadium.getId());
        long newId = db.insert(
                MatchContract.Match.TABLE_NAME,
                null,
                cv
        );
        cv = new ContentValues();
        cv.put(RelMatchTeamContract.RelMatchTeam.TEAM, team1.getId());
        cv.put(RelMatchTeamContract.RelMatchTeam.MATCH, newId);
        db.insert(
                RelMatchTeamContract.RelMatchTeam.TABLE_NAME,
                null,
                cv
        );
        cv = new ContentValues();
        cv.put(RelMatchTeamContract.RelMatchTeam.TEAM, team2.getId());
        cv.put(RelMatchTeamContract.RelMatchTeam.MATCH, newId);
        db.insert(
                RelMatchTeamContract.RelMatchTeam.TABLE_NAME,
                null,
                cv
        );
    }
}
