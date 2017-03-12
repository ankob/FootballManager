package com.footballmanager.admin_fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.admin_fragments.edit_dialogs.MatchEditDialog;
import com.footballmanager.db.MatchContract;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Match;
import com.footballmanager.model.Team;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Andrew on 12-Mar-17.
 */

public class MatchItemFragment extends ItemFragment {
    @Override
    protected String getTableName() {
        return MatchContract.Match.TABLE_NAME;
    }

    @Override
    protected String getIdColumnName() {
        return MatchContract.Match._ID;
    }

    @Override
    protected MappedItem getEmptyItem() {
        return new Match(
                -1,
                new Date(),
                0,
                null,
                "",
                new LinkedList<Team>()
        );
    }

    @Override
    protected MappedItem makeInstance(Cursor cursor, SQLiteDatabase db) {
        return Match.makeInstance(cursor, db);
    }

    @Override
    protected String[] getProjection() {
        return MatchContract.Match.projection;
    }

    @Override
    protected EntityEditDialog getEntityEditDialog(MappedItem item) {
        return new MatchEditDialog((Match) item);
    }
}
