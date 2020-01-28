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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(Intent.ACTION_SEND);

    }

    public void callIntent(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button1:

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

