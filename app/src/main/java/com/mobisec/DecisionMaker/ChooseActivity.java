package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobisec.DecisionMaker.model.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends Activity {

    private List<EventActivity> activities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_confirm = (Button)findViewById(R.id.confirmButton);
        final TextView EventName = findViewById(R.id.title);

        activities = new ArrayList<>();

        AddActivityListAdapter adapter = new AddActivityListAdapter(this, activities);

        ListView listView = findViewById(R.id.listActivities);
        listView.setAdapter(adapter);



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
