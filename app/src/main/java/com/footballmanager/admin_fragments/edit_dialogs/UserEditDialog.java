package com.footballmanager.admin_fragments.edit_dialogs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;
import com.footballmanager.db.RoleContract;
import com.footballmanager.db.UserContract;
import com.footballmanager.model.Role;
import com.footballmanager.model.User;

import java.util.List;

/**
 * Created by Andrew on 11-Mar-17.
 */

public class UserEditDialog extends EntityEditDialog {

    private static User user;
    public UserEditDialog() { super(); }

    public UserEditDialog(User user) {
        super();
        UserEditDialog.user = user;
    }

    private class RoleAdapter extends ArrayAdapter<Role> {

        public RoleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Role> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.role_spinner_layout, null);
            ((TextView) result.findViewById(R.id.role_name_label)).setText(getItem(position).getName());
            return result;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }

    @Override
    public String getTitle() {
        return "Edit User";
    }

    @Override
    public boolean saveInstance(DbHelper dbHelper) {
        EditText editName = (EditText) getDialog().findViewById(R.id.edit_user_name);
        EditText editPass = (EditText) getDialog().findViewById(R.id.edit_password);
        EditText repeatPass = (EditText) getDialog().findViewById(R.id.repeat_password);
        Spinner selectRole = (Spinner) getDialog().findViewById(R.id.roles_spinner);

        if(!editPass.getText().toString().equals(repeatPass.getText().toString())) {
            repeatPass.setError(getString(R.string.not_equal_passwords_message));
            editPass.setError("");
            return false;
        }

        if (user.getId() == -1)
            UserContract.addNewUser(
                    editName.getText().toString(),
                    editPass.getText().toString(),
                    (Role) selectRole.getSelectedItem(),
                    dbHelper.getWritableDatabase()
            );
        else
            UserContract.updateUser(
                    user.getId(),
                    editName.getText().toString(),
                    editPass.getText().toString(),
                    (Role) selectRole.getSelectedItem(),
                    dbHelper.getWritableDatabase()
            );
        return true;
    }

    @Override
    public View getDialogLayoutView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View result = inflater.inflate(R.layout.fragment_edit_user, null);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        EditText editName = (EditText) result.findViewById(R.id.edit_user_name);
        editName.setText(user.getName());
        Spinner rolesSpinner = (Spinner) result.findViewById(R.id.roles_spinner);
        List<Role> roles = RoleContract.getRoles(db);
        rolesSpinner.setAdapter(new RoleAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                roles
        ));
        int selectionIndex = 0;
        for (; selectionIndex < roles.size(); selectionIndex++) {
            if(roles.get(selectionIndex).getId() == user.getRole().getId())
                break;
        }
        rolesSpinner.setSelection(selectionIndex);

        return result;
    }
}
