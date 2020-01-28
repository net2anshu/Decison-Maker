package com.mobisec.DecisionMaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobisec.DecisionMaker.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static java.lang.Integer.parseInt;

public class NewEventActivity extends Activity {

    private List<EventActivity> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);

        String eventId = RandomUtils.randomString(6);

        TextView codeTextView = findViewById(R.id.codeTextView);
        codeTextView.setText(eventId);

        activities = new ArrayList<>();
        activities.add(new EventActivity("Test", 1, 10));
        activities.add(new EventActivity("Test2", 5, 5));

        AddActivityListAdapter adapter = new AddActivityListAdapter(this, activities);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        Button addActivity = findViewById(R.id.addActivity);
        addActivity.setOnClickListener(this::popup);

        Button close = findViewById(R.id.closebutton);
        close.setOnClickListener(view -> finish());

        Button shuffle = findViewById(R.id.reassignbutton);
        if (hasOverloadedActivities()) {
            shuffle.setEnabled(true);
        }

        shuffle.setOnClickListener(view -> reassignRandomly());
    }

    private void reassignRandomly() {
        // TODO
    }

    private boolean hasOverloadedActivities() {
        for (EventActivity a : activities) {
            if (a.getRegistered() > a.getAvailable()) {
                return true;
            }
        }
        return false;
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
            /*
            MyContact contact = new MyContact(name.getText().toString(), number.getText().toString());
            getInstance().getReference("Contacts").child(Integer.toString(contact.hashCode())).setValue(contact);
            adapter.notifyDataSetChanged();
            */

            String eventName = name.getText().toString();
            String nrParticipants = number.getText().toString();
            EventActivity e = new EventActivity(eventName, 0, parseInt(nrParticipants));
            activities.add(e);
            popupWindow.dismiss();
        });
    }

}
