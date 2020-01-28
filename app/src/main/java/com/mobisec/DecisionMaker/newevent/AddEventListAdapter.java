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
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.EventActivity;

import java.util.List;

public class AddEventListAdapter extends ArrayAdapter<Event> {

    public AddEventListAdapter(@NonNull Context context, @NonNull List<Event> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event= getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_row, parent, false);
        }

        TextView event_name = convertView.findViewById(R.id.event_name);
        event_name.setText(event.getName());
        return convertView;
    }

}
