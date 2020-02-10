package com.mobisec.DecisionMaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.view.View;

        import androidx.appcompat.app.AppCompatActivity;

        import com.mobisec.DecisionMaker.EventListActivity;
        import com.mobisec.DecisionMaker.JoinEvent;
        import com.mobisec.DecisionMaker.NewEventActivity;
        import com.mobisec.DecisionMaker.R;

        import java.util.UUID;




public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

    public void callIntent(View view) {

                startActivity(new Intent(this, MainActivity.class));



    }
}

