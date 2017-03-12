package com.footballmanager.admin_fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.admin_fragments.edit_dialogs.TeamEditDialog;
import com.footballmanager.db.TeamContract;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Team;
import com.footballmanager.model.User;

import java.util.LinkedList;


/**
 * Created by Andrew on 12-Mar-17.
 */

public class TeamItemFragment extends ItemFragment {
    @Override
    protected String getTableName() {
        return TeamContract.Team.TABLE_NAME;
    }

    @Override
    protected String getIdColumnName() {
        return TeamContract.Team._ID;
    }

    @Override
    protected MappedItem getEmptyItem() {
        return new Team(-1, "", new LinkedList<User>());
    }

    @Override
    protected MappedItem makeInstance(Cursor cursor, SQLiteDatabase db) {
        return Team.makeInstance(cursor, db);
    }

    @Override
    protected String[] getProjection() {
        return TeamContract.Team.projection;
    }

    @Override
    protected EntityEditDialog getEntityEditDialog(MappedItem item) {
        return new TeamEditDialog((Team) item);
    }
}
