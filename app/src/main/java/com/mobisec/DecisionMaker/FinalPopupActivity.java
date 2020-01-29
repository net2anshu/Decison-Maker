package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mobisec.DecisionMaker.adapter.FinalListAdapter;
import com.mobisec.DecisionMaker.model.User;

import java.util.List;

public class FinalPopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalpopup);

        Intent i = getIntent();
        List<User> users = (List<User>) i.getSerializableExtra("users");

        View v = findViewById(android.R.id.content).getRootView();

        FinalListAdapter adapter = new FinalListAdapter(v.getContext(), users);

        ListView finalpopup= findViewById(R.id.finalpopup_list);
        finalpopup.setAdapter(adapter);
    }

}
