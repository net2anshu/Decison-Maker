package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobisec.DecisionMaker.utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class EventListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> stringSet = mPreferences.getStringSet(Constants.USER_EVENTS, new HashSet<>());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("events").child("name");







    }

}
