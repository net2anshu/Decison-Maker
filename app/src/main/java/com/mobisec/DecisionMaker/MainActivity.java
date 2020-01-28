package com.mobisec.DecisionMaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(Intent.ACTION_SEND);

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

