package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobisec.DecisionMaker.adapter.AddActivityListAdapter;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.EventActivity;
import com.mobisec.DecisionMaker.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseActivity extends Activity {

    private List<EventActivity> activities;

    List<String> ActivityIds;
    View previousView;
    boolean color = false;
    String userId;
    EventActivity eventActivity;
    EventActivity read_activity;
    Event read_event;
    Event actual_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);
        Button button = findViewById(R.id.confirmButton);
        final TextView EventName = findViewById(R.id.title);
        final FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database.getReference("events");
        final DatabaseReference myRef2 = database.getReference("activities");
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        activities = new ArrayList<>();

        AddActivityListAdapter adapter = new AddActivityListAdapter(this, activities);

        ListView listView = findViewById(R.id.listActivities);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            userId = mPreferences.getString("id","default");
            eventActivity = activities.get(position);

            if (!color){
                view.setBackgroundColor(Color.CYAN);
                previousView = view;
                color = true;
            } else {
                previousView.setBackgroundColor(Color.WHITE);
                view.setBackgroundColor(Color.CYAN);
                previousView = view;
            }
        });

        String eventIntent = getIntent().getStringExtra("event");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activities.clear();
                adapter.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     read_event = snapshot.getValue(Event.class);
                    String eventId = read_event.getId();
                    if (eventId.equals(eventIntent)) {
                        ActivityIds = read_event.getActivities();
                        EventName.setText(read_event.getName());
                        actual_event = read_event;
                    }
                    adapter.notifyDataSetChanged();

                }

                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        activities.clear();
                        adapter.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            read_activity = snapshot.getValue(EventActivity.class);
                            if (ActivityIds != null){

                                for (int i=0;i < ActivityIds.size();i++){
                                    if(ActivityIds.get(i).equals(read_activity.getId())){
                                        activities.add(read_activity);
                                    }
                                }
                            }

                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        button.setOnClickListener(view -> {
            Toast.makeText(
                    ChooseActivity.this,
                    "Activity " + read_activity.getName() + " chosen",
                    Toast.LENGTH_SHORT).show();

            eventActivity.getregisteredUsers().add(userId);
            myRef2.child(eventActivity.getId()).setValue(eventActivity);

            SharedPreferences mPreferences1 = PreferenceManager.getDefaultSharedPreferences(ChooseActivity.this);
            Set<String> set = mPreferences1.getStringSet(Constants.USER_EVENTS, new HashSet<>());
            set.add(actual_event.getId());
            SharedPreferences.Editor mEditor = mPreferences1.edit();
            mEditor.putStringSet(Constants.USER_EVENTS, set);
            Toast.makeText(ChooseActivity.this, set.toString(),Toast.LENGTH_SHORT).show();
            mEditor.commit();

            finish();
            startActivity(new Intent(ChooseActivity.this, MainActivity.class));
        });
    }
}
