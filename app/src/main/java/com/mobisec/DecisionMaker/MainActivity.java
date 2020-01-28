package com.mobisec.DecisionMaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
