package com.mobisec.DecisionMaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobisec.DecisionMaker.R;
import com.mobisec.DecisionMaker.model.User;

import java.util.List;

public class FinalListAdapter extends ArrayAdapter<User> {

    public FinalListAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.finalpopup_row, parent, false);
        }

        TextView name = convertView.findViewById(R.id.finalpop_elem);
        name.setText((position+1) + " " + user.getName());

        return convertView;
    }
}
