package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class JoinEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_joinevent = (Button)findViewById(R.id.button2);
        final EditText name = findViewById(R.id.editText);
        final String playerName = name.getText().toString();
        final EditText event = findViewById(R.id.editText);
        final String eventName = name.getText().toString();


        btn_joinevent.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Toast.makeText(JoinEvent.this,
                    "Button clicked index = " + name, Toast.LENGTH_SHORT)
                    .show();

            SharedPreferences mPrefs = getSharedPreferences("User_File", MODE_PRIVATE); //add key
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            String data = mPrefs.getString("userID", null);

            User user = new User();
            user.setuser(data, playerName, eventName);
        }
}); }

    private static final AtomicInteger counter = new AtomicInteger();
    public static int nextValue() {
        return counter.getAndIncrement();
    }
}



