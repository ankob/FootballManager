package com.footballmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.footballmanager.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Hello, " + User.getInstance().getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.preferences_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.manage_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminActivity = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(adminActivity);
            }
        });
        fab.setVisibility(User.getInstance().getRole().getName().equals("Manager") ? View.VISIBLE : View.GONE);
    }

}
