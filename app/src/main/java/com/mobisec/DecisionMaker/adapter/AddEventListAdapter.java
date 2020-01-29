package com.mobisec.DecisionMaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobisec.DecisionMaker.FinalizedActivity;
import com.mobisec.DecisionMaker.NewEventActivity;
import com.mobisec.DecisionMaker.R;
import com.mobisec.DecisionMaker.model.Event;
import com.mobisec.DecisionMaker.utils.Constants;

import java.util.List;

public class AddEventListAdapter extends ArrayAdapter<Event> {

    private Context context;

    public AddEventListAdapter(@NonNull Context context, @NonNull List<Event> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Event event= getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_row, parent, false);
        }

        String check_ID = event.getAdminId();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String local_ID = mPrefs.getString(Constants.USER_ID, null);

        Button button = convertView.findViewById(R.id.button1);
        button.setEnabled(false);
        if (check_ID.equals(local_ID) && !event.isFinalized()) {
            button.setEnabled(true);
            button.setOnClickListener(v -> {
                Intent intent = new Intent(context, NewEventActivity.class);
                intent.putExtra("event", event.getId());
                getContext().startActivity(intent);
            });
        }

        boolean status = event.isFinalized();
        Button button2 = convertView.findViewById(R.id.button2);
        button2.setEnabled(false);
        if (status) {
            button2.setEnabled(true);

            button2.setOnClickListener(v -> {
                Intent intent = new Intent(context, FinalizedActivity.class).putExtra("event", event.getId());
                context.startActivity(intent);
            });
        }

        TextView event_name = convertView.findViewById(R.id.event_name);
        event_name.setText(event.getName());
        return convertView;




    }
}
