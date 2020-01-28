package com.mobisec.DecisionMaker.newevent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobisec.DecisionMaker.R;
import com.mobisec.DecisionMaker.model.EventActivity;

import java.util.List;

public class AddActivityListAdapter extends ArrayAdapter<EventActivity> {

    public AddActivityListAdapter(@NonNull Context context, @NonNull List<EventActivity> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EventActivity eventActivity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.new_event_activity_row, parent, false);
        }

        TextView name = convertView.findViewById(R.id.activityName);
        TextView participants = convertView.findViewById(R.id.activityparticipants);

        name.setText(eventActivity.getName());
        participants.setText(eventActivity.getRegistered() + "/" + eventActivity.getAvailable());

        if (eventActivity.getRegistered() > eventActivity.getAvailable()) {
            participants.setTextColor(Color.RED);
        }

        return convertView;
    }

}
