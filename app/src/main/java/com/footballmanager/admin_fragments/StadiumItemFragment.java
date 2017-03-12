package com.footballmanager.admin_fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.admin_fragments.edit_dialogs.StadiumEditDialog;
import com.footballmanager.db.DbHelper;
import com.footballmanager.db.StadiumContract;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Stadium;

import java.util.LinkedList;
import java.util.List;

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
    protected List<MappedItem> getData() {
        List<MappedItem> result = new LinkedList<>();
        SQLiteDatabase db = new DbHelper(getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                getTableName(),
                StadiumContract.Stadium.projection,
                "",
                null,
                "",
                "",
                ""
        );
        while (cursor.moveToNext())
            result.add(Stadium.makeInstance(cursor, db));
        return result;
    }

    @Override
    protected EntityEditDialog getEntityEditDialog(MappedItem item) {
        return new StadiumEditDialog((Stadium) item);
    }
}
