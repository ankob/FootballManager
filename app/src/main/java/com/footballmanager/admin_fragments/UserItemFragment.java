package com.footballmanager.admin_fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.footballmanager.db.RoleContract;
import com.footballmanager.db.UserContract;
import com.footballmanager.admin_fragments.edit_dialogs.EntityEditDialog;
import com.footballmanager.admin_fragments.edit_dialogs.UserEditDialog;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Role;
import com.footballmanager.model.User;

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
    protected MappedItem makeInstance(Cursor cursor, SQLiteDatabase db) {
        return User.makeInstance(cursor, db);
    }

    @Override
    protected String[] getProjection() {
        return UserContract.User.projection;
    }

    @Override
    protected EntityEditDialog getEntityEditDialog(MappedItem item) {
        return new UserEditDialog((User) item);
    }
}
