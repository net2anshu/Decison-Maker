package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobisec.DecisionMaker.adapter.AddEventListAdapter;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.utils.Constants;
import com.mobisec.DecisionMaker.utils.SimpleValueListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EventListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist);

        Button btnAdd = findViewById(R.id.button_home);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(EventListActivity.this, MainActivity.class);
            startActivity(intent);
        });


        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> stringSet = mPreferences.getStringSet(Constants.USER_EVENTS, new HashSet<>());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("events");

        final ArrayList<Event> eventsArray = new ArrayList<>();
        final AddEventListAdapter adapter = new AddEventListAdapter(this, eventsArray);
        ref.addValueEventListener(new SimpleValueListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot val : dataSnapshot.getChildren()){
                    if (stringSet.contains(val.getKey())){
                        Event read_event = val.getValue(Event.class);
                        eventsArray.add(read_event);
                        adapter.notifyDataSetInvalidated();
                    }
                }
            }
        });

        ListView listView = findViewById(R.id.list_event);
        listView.setAdapter(adapter);
    }

}


