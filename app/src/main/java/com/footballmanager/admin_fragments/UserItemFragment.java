package com.footballmanager.admin_fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.DbHelper;
import com.footballmanager.db.RoleContract;
import com.footballmanager.db.UserContract;
import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.admin_fragments.edit_dialogs.UserEditDialog;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Role;
import com.footballmanager.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 12-Mar-17.
 */

public class UserItemFragment extends ItemFragment {
    @Override
    protected MappedItem getEmptyItem() {
        return new User(
                -1,
                "",
                new Role(RoleContract.MANGER_ROLE_ID, "")
        );
    }

    @Override
    protected String getTableName() {
        return UserContract.User.TABLE_NAME;
    }

    @Override
    protected String getIdColumnName() {
        return UserContract.User._ID;
    }

    @Override
    protected List<MappedItem> getData() {
        SQLiteDatabase db = new DbHelper(getContext()).getReadableDatabase();
        List<MappedItem> users = new LinkedList<>();
        Cursor cursor = db.query(
                UserContract.User.TABLE_NAME,
                UserContract.User.projection,
                "",
                null,
                "",
                "",
                ""
        );
        while (cursor.moveToNext())
            users.add(User.makeInstance(cursor, db));
        return users;
    }

    @Override
    protected EntityEditDialog getEntityEditDialog(MappedItem item) {
        return new UserEditDialog((User) item);
    }
}
