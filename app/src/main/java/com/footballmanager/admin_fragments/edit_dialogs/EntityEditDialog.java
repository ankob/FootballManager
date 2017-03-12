package com.footballmanager.admin_fragments.edit_dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;

/**
 * Created by Andrew on 09-Mar-17.
 */

public abstract class EntityEditDialog extends DialogFragment {

    abstract public String getTitle();
    abstract public boolean saveInstance(DbHelper sql);
    abstract public View getDialogLayoutView();
    protected DbHelper dbHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dbHelper = new DbHelper(getActivity().getBaseContext());
        builder.setTitle(getTitle())
                .setView(getDialogLayoutView())
                .setPositiveButton(
                        R.string.entity_edit_dialog_save_button_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!saveInstance(dbHelper))
                                    dialogInterface.dismiss();
                            }
                        }
                ).setNegativeButton(
                        R.string.entity_edit_dialog_cancel_button_label,
                        null
                );
        return builder.create();
    }
}
