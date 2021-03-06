package com.footballmanager.admin_fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.admin_fragments.edit_dialogs.StadiumEditDialog;
import com.footballmanager.db.StadiumContract;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Stadium;


/**
 * Created by Andrew on 12-Mar-17.
 */

public class StadiumItemFragment extends ItemFragment {
    @Override
    protected String getTableName() {
        return StadiumContract.Stadium.TABLE_NAME;
    }

    @Override
    protected String getIdColumnName() {
        return StadiumContract.Stadium._ID;
    }

    @Override
    protected MappedItem getEmptyItem() {
        return new Stadium(-1, "", 0);
    }

    @Override
    protected MappedItem makeInstance(Cursor cursor, SQLiteDatabase db) {
        return Stadium.makeInstance(cursor, db);
    }

    @Override
    protected String[] getProjection() {
        return StadiumContract.Stadium.projection;
    }

    @Override
    protected EntityEditDialog getEntityEditDialog(MappedItem item) {
        return new StadiumEditDialog((Stadium) item);
    }
}
