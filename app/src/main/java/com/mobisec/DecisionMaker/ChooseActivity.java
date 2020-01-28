package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.EventActivity;
import com.mobisec.DecisionMaker.newevent.AddActivityListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChooseActivity extends Activity {

    private List<EventActivity> activities;



    List<String> ActivityIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);
        Button btn_confirm = (Button)findViewById(R.id.confirmButton);
        final TextView EventName = findViewById(R.id.title);
        final FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database.getReference("Events");
        final DatabaseReference myRef2 = database.getReference("activities");


        activities = new ArrayList<>();



        AddActivityListAdapter adapter = new AddActivityListAdapter(this, activities);

        ListView listView = findViewById(R.id.listActivities);
        listView.setAdapter(adapter);

        String eventIntent = getIntent().getStringExtra("event");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activities.clear();
                adapter.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = new Event();

                    Event read_event = snapshot.getValue(Event.class);
                    String eventId = read_event.getId();
                    Log.i("DEXISIONMAKER",eventIntent);
                    Log.i("DEXISIONMAKER",eventId);
                    if (eventId.equals(eventIntent)) {
                        ActivityIds = read_event.getActivities();
                        EventName.setText(read_event.getName());
                    }
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activities.clear();
                adapter.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventActivity eventActivity = new EventActivity();

                    EventActivity read_activity = snapshot.getValue(EventActivity.class);
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        btn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(ChooseActivity.this,
                        "Activity (?) chosen", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
    }
}
