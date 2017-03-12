package com.footballmanager.admin_fragments.edit_dialogs;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;
import com.footballmanager.db.StadiumContract;
import com.footballmanager.model.Stadium;

/**
 * Created by Andrew on 12-Mar-17.
 */

public class StadiumEditDialog extends EntityEditDialog {

    private static Stadium stadium;

    public StadiumEditDialog() {
    }

    public StadiumEditDialog(Stadium stadium) {
        this.stadium = stadium;
    }

    @Override
    public String getTitle() {
        return "Edit Stadium";
    }

    @Override
    public boolean saveInstance(DbHelper dbHelper) {
        if (stadium.getId() == -1) {
            StadiumContract.saveStadium(
                    ((EditText) getDialog().findViewById(R.id.edit_name)).getText().toString(),
                    Integer.parseInt(((EditText) getDialog().findViewById(R.id.edit_fields)).getText().toString()),
                    dbHelper.getWritableDatabase()
            );
        } else {
            StadiumContract.updateStadium(
                    stadium.getId(),
                    ((EditText) getDialog().findViewById(R.id.edit_name)).getText().toString(),
                    Integer.parseInt(((EditText) getDialog().findViewById(R.id.edit_fields)).getText().toString()),
                    dbHelper.getWritableDatabase()
            );
        }
        return true;
    }

    @Override
    public View getDialogLayoutView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View result = inflater.inflate(R.layout.fragment_edit_stadium, null);

        EditText editName = (EditText) result.findViewById(R.id.edit_name);
        editName.setText(stadium.getName());
        EditText editFields = (EditText) result.findViewById(R.id.edit_fields);
        editFields.setText(Integer.toString(stadium.getFields()));

        return result;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
