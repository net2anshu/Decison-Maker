package com.mobisec.DecisionMaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        id = mPreferences.getString("id","default");

        if (id.equals("default")) {
            id = UUID.randomUUID().toString();
            mEditor.putString("id",id);
            mEditor.commit();
        }
    }

    public void callIntent(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, NewEventActivity.class));
                break;

            case R.id.button2:
                break;

            case R.id.button3:
                break;

            case R.id.button4:
                finish();
                break;
        }

    }
}

