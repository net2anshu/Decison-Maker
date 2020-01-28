package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.EventActivity;
import com.mobisec.DecisionMaker.newevent.AddActivityListAdapter;
import com.mobisec.DecisionMaker.utils.Constants;
import com.mobisec.DecisionMaker.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class NewEventActivity extends Activity {

    private List<EventActivity> activities;

    private String eventId;
    private TextView name;
    private Button shuffle;
    private AddActivityListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);

        TextView codeTextView = findViewById(R.id.codeTextView);
        name = findViewById(R.id.namefield);

        shuffle = findViewById(R.id.reassignbutton);
        activities = new ArrayList<>();

        adapter = new AddActivityListAdapter(this, activities);

        String stringExtra = getIntent().getStringExtra("event");
        if (isNull(stringExtra)) {
            eventId = RandomUtils.randomString(6);
        } else {
            eventId = stringExtra;
            getInstance().getReference("events")
                    .child(eventId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Event value = dataSnapshot.getValue(Event.class);
                            name.setText(value.getName());

                            getInstance().getReference("activities")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                EventActivity value1 = snapshot.getValue(EventActivity.class);
                                                if (value.getActivities().contains(value1.getId())) {
                                                    activities.add(value1);
                                                }
                                            }
                                            hasOverloadedActivities();
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                                    });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }

        codeTextView.setText(eventId);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        Button addActivity = findViewById(R.id.addActivity);
        addActivity.setOnClickListener(this::popup);

        Button close = findViewById(R.id.submitbutton);
        close.setOnClickListener(view -> submit());

        shuffle.setOnClickListener(view -> reassignRandomly());

        Button submit = findViewById(R.id.finalizebutton);
        submit.setOnClickListener(view -> finalizeEvent());
    }

    private void submit() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = pref.getString(Constants.USER_ID, "");

        List<String> ids = activities.stream().map(EventActivity::getId).collect(toList());
        Event event = new Event(eventId, name.getText().toString(), ids, userId);
        getInstance().getReference("events").child(eventId).setValue(event);

        activities.forEach(eventActivity -> getInstance()
                .getReference("activities")
                .child(eventActivity.getId())
                .setValue(eventActivity));

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> stringSet = mPreferences.getStringSet(Constants.USER_EVENTS, new HashSet<>());

        SharedPreferences.Editor mEditor = mPreferences.edit();
        stringSet.add(eventId);
        mEditor.putStringSet(Constants.USER_EVENTS, stringSet);
        mEditor.commit();

        Toast.makeText(this, "Event submitted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void reassignRandomly() {
        List<String> users = activities.stream()
                .map(EventActivity::getregisteredUsers)
                .flatMap(Collection::stream)
                .collect(toList());

        Collections.shuffle(users);

        Stack<String> stack = new Stack<>();
        stack.addAll(users);

        activities.forEach(activity -> {
            activity.setregisteredUsers(new ArrayList<>());
        });

        boolean run = true;
        while (run) {
            for (EventActivity eventActivity : activities) {
                if (stack.isEmpty()) {
                    run = false;
                    break;
                }
                if (eventActivity.getRegistered() < eventActivity.getAvailable()) {
                    eventActivity.getregisteredUsers().add(stack.pop());
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void finalizeEvent() {
        // TODO
    }

    private void hasOverloadedActivities() {
        for (EventActivity a : activities) {
            if (a.getRegistered() > a.getAvailable()) {
                shuffle.setEnabled(true);
                return;
            }
        }
    }

    public void popup(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activitypopup, null);

        PopupWindow popupWindow = new PopupWindow(popupView, MATCH_PARENT, MATCH_PARENT, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText name = popupView.findViewById(R.id.editText3);
        EditText number = popupView.findViewById(R.id.editText5);

        Button button = popupView.findViewById(R.id.button3);
        button.setOnClickListener(view1 -> {
            String eventName = name.getText().toString();
            int nrParticipants = Integer.parseInt(number.getText().toString());

            String id = UUID.randomUUID().toString();
            EventActivity eventActivity = new EventActivity(id, eventName, nrParticipants, new ArrayList<>());
            getInstance().getReference("activities").child(id).setValue(eventActivity);
            activities.add(eventActivity);

            popupWindow.dismiss();
        });
    }

}
