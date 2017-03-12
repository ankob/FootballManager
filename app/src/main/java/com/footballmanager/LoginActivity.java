package com.footballmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.footballmanager.db.DbHelper;
import com.footballmanager.db.UserContract;
import com.footballmanager.model.User;

public class LoginActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(getApplicationContext());
        findViewById(R.id.login_button).setOnClickListener(loginButtonOnClickListener);
    }

    private View.OnClickListener loginButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String pass = ((EditText) findViewById(R.id.edit_user_password)).getText().toString();
            String name = ((EditText) findViewById(R.id.edit_user_name)).getText().toString();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            User currentUser = UserContract.getUser(name, pass, db);
            if (currentUser != null) {
                User.setInstance(currentUser);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            } else  {
                Snackbar.make(
                        view,
                        getString(R.string.wrong_user_data_message),
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        }
    };
}
