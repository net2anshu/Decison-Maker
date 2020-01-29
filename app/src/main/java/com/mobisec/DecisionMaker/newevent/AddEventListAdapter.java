package com.mobisec.DecisionMaker.newevent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobisec.DecisionMaker.EventListActivity;
import com.mobisec.DecisionMaker.JoinEvent;
import com.mobisec.DecisionMaker.MainActivity;
import com.mobisec.DecisionMaker.NewEventActivity;
import com.mobisec.DecisionMaker.R;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.model.EventActivity;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class AddEventListAdapter extends ArrayAdapter<Event> {

    Context context;

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


        Button button = convertView.findViewById(R.id.button1);         //Admin button implemention
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NewEventActivity.class);
                        intent.putExtra("eventID",event.getId());
                getContext().startActivity(intent);
            }
        });

        boolean status = event.isFinalized();
        Button button2 = convertView.findViewById(R.id.button2);         //User button implemention
        button2.setEnabled(false);
        if (status) {
            button2.setEnabled(true);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // To do
                    Intent intent = new Intent(getContext(), MainActivity.class)
                            .putExtra("eventID", event.getId());
                    getContext().startActivity(intent);
                }
            });
        }

        TextView event_name = convertView.findViewById(R.id.event_name);
        event_name.setText(event.getName());
        return convertView;




    }
}
