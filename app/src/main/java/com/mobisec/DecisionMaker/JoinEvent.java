package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.User;
import com.mobisec.DecisionMaker.utils.Constants;
import com.mobisec.DecisionMaker.utils.SimpleValueListener;

import java.util.Optional;
import java.util.Spliterator;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static java.util.stream.StreamSupport.stream;

public class JoinEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Button join = findViewById(R.id.button2);
        EditText name = findViewById(R.id.editText);
        EditText event = findViewById(R.id.editText2);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(JoinEvent.this);
        String userId = mPrefs.getString(Constants.USER_ID, null);

        join.setOnClickListener(view -> {
            String eventId = event.getText().toString();
            String playerName = name.getText().toString();

            User user = new User(userId, playerName);
            getInstance().getReference("users").child(userId).setValue(user);

           getInstance().getReference()
                   .child("events")
                   .addListenerForSingleValueEvent(getEventListener(eventId));
        });
    }

    private ValueEventListener getEventListener(String eventId) {
        return new SimpleValueListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Spliterator<DataSnapshot> spliterator = dataSnapshot.getChildren().spliterator();
                Optional<Event> eventOpt = stream(spliterator, false)
                        .map(i -> i.getValue(Event.class))
                        .filter(i -> i.getId().equals(eventId))
                        .findFirst();

                if (!eventOpt.isPresent()) {
                    Toast.makeText(JoinEvent.this, "Event does not exist!", Toast.LENGTH_SHORT).show();
                } else {
                    Event event = eventOpt.get();
                    if (event.isFinalized()) {
                        Toast.makeText(JoinEvent.this, "Event is already finalized!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(JoinEvent.this, ChooseActivity.class);
                        intent.putExtra("event", event.getId());
                        startActivity(intent);
                    }
                }
            }
        };
    }

}



