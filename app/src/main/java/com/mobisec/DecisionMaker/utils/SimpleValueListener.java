package com.mobisec.DecisionMaker.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class SimpleValueListener implements ValueEventListener {

    @Override
    public abstract void onDataChange(@NonNull DataSnapshot dataSnapshot);

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.e(this.getClass().getSimpleName(), databaseError.getDetails());
    }

}
