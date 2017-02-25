package com.footballmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.footballmanager.db.DbHelper;
import com.footballmanager.db.RoleContract;
import com.footballmanager.db.UserContract;
import com.footballmanager.model.Role;
import com.footballmanager.model.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Spinner rolesSpinner;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Role> roles = RoleContract.getRoles(db);
        rolesSpinner = (Spinner) findViewById(R.id.roles_spinner);
        RolesAdapter rolesAdapter = new RolesAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                roles
        );
        rolesSpinner.setAdapter(rolesAdapter);
        findViewById(R.id.roles_spinner).setVisibility(View.GONE);
        findViewById(R.id.new_user_checkbox).setOnClickListener(checkBoxOnClickListener);
        findViewById(R.id.login_button).setOnClickListener(loginButtonOnClickListener);
    }

    private View.OnClickListener checkBoxOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox newUser = (CheckBox) view;
            rolesSpinner.setVisibility(
                    newUser.isChecked() ? View.VISIBLE : View.GONE
            );
            findViewById(R.id.user_password_copy).setEnabled(newUser.isChecked());
        }
    };

    private View.OnClickListener loginButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String pass = ((EditText) findViewById(R.id.edit_user_password)).getText().toString();
            EditText repeatPassword = (EditText) findViewById(R.id.user_password_copy);
            String name = ((EditText) findViewById(R.id.edit_user_name)).getText().toString();
            if (!repeatPassword.getText().toString().equals(pass)) {
                repeatPassword.setError(getString(R.string.not_equal_passwords_message));
                return;
            }
            if (((CheckBox) findViewById(R.id.new_user_checkbox)).isChecked()) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Role role = (Role) rolesSpinner.getSelectedItem();
                User newUser = UserContract.addNewUser(name, pass, role, db);
                User.setInstance(newUser);
                Snackbar.make(
                        findViewById(R.id.activity_login),
                        "Hello, " + User.getInstance().getName(),
                        Snackbar.LENGTH_SHORT
                );
            } else {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                User currentUser = UserContract.getUser(name, pass, db);
                if (currentUser != null) {
                    User.setInstance(currentUser);
                } else  {
                    Snackbar.make(
                            findViewById(R.id.activity_login),
                            "Wrong user data.",
                            Snackbar.LENGTH_SHORT
                    );
                }
            }
        }
    };

    class RolesAdapter extends ArrayAdapter<Role> {
        public RolesAdapter(Context context, int resource, List<Role> objects) {
            super(context, resource, objects);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = getLayoutInflater();
            View result = li.inflate(R.layout.fragment_role_view, parent, false);
            ((TextView) result.findViewById(R.id.role_name_label)).setText(getItem(position).getName());
            return result;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }
}
