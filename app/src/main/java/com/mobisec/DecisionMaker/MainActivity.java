package com.mobisec.DecisionMaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobisec.DecisionMaker.utils.Constants;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEditor = mPreferences.edit();

        String id = mPreferences.getString(Constants.USER_ID,"default");

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
                startActivity(new Intent(this, JoinEvent.class));
                break;

            case R.id.button3:
                startActivity(new Intent(this, EventListActivity.class));
                break;

            case R.id.button4:
                finish();
                break;
        }

    }
}

