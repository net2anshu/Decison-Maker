package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.User;
import com.mobisec.DecisionMaker.utils.Constants;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static java.util.Objects.isNull;

public class JoinEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Button join = findViewById(R.id.button2);
        final EditText name = findViewById(R.id.editText);
        final String playerName = name.getText().toString();

        final EditText event = findViewById(R.id.editText);
        final String eventName = event.getText().toString();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(JoinEvent.this);
        String userId = mPrefs.getString(Constants.USER_ID, null);

        join.setOnClickListener(view -> {
            User user = new User(userId, playerName);
            getInstance().getReference("users").child(userId).setValue(user);

            DatabaseReference eventRef = getInstance().getReference("events").child(eventName);

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Event e = dataSnapshot.getValue(Event.class);
                    if (isNull(e)) {
                        Toast.makeText(JoinEvent.this, "Event does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(JoinEvent.this, "Forward to next view!", Toast.LENGTH_SHORT).show();
                        // TODO: Forward with an intent

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };

            eventRef.addListenerForSingleValueEvent(eventListener);
        });
    }

}



