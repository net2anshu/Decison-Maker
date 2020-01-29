package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mobisec.DecisionMaker.adapter.AddActivityListAdapter;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.EventActivity;
import com.mobisec.DecisionMaker.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class FinalizedActivity extends Activity {

    private List<EventActivity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalized);

        AddActivityListAdapter adapter = new AddActivityListAdapter(this, activities);

        String eventId = getIntent().getStringExtra("event");

        getInstance().getReference("events")
                .child(eventId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Event event = dataSnapshot.getValue(Event.class);
                        parseEvent(event);

                        getInstance().getReference("activities")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            EventActivity value1 = snapshot.getValue(EventActivity.class);
                                            if (event.getActivities().contains(value1.getId())) {
                                                activities.add(value1);
                                            }
                                        }

                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        Intent i = new Intent(this, MainActivity.class);
        findViewById(R.id.to_home).setOnClickListener(view -> startActivity(i));

        ListView listView = findViewById(R.id.listActivities2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i1, l) -> {
            EventActivity eventActivity = activities.get(i1);
            List<String> strings = eventActivity.getregisteredUsers();
            getInstance().getReference("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<User> users = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User value = snapshot.getValue(User.class);
                                if (strings != null && strings.contains(value.getId())) {
                                    users.add(value);
                                }
                            }
                            if (!users.isEmpty()) {
                                popup(users);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        });
    }

    public void popup(List<User> users) {
        Intent i = new Intent(FinalizedActivity.this, FinalPopupActivity.class);
        i.putExtra("users", (Serializable) users);
        startActivity(i);
    }

    private void parseEvent(Event event) {
        TextView event_name = findViewById(R.id.event_name);
        event_name.setText(event.getName());
    }

}
