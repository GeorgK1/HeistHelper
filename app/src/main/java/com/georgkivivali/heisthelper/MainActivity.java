package com.georgkivivali.heisthelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import com.georgkivivali.heisthelper.activities.SaveHeistActivity;

public class MainActivity extends AppCompatActivity {

    Button startHeistButton;
    Button endHeistButton;
    Chronometer chronometer;
    public static final String EXTRA_TIME = "com.georgkivivali.heisthelper.EXTRA_TIME";

    long heistDuration;

    public void toggleEndButtonVisibility(boolean visible) {
        if(visible) {
            //When startheist button is clicked (heist ongoing)
            endHeistButton.setVisibility(Button.VISIBLE);
            startHeistButton.setVisibility(Button.INVISIBLE);

        } else {
            //When endheist button is clicked (heist stopped)
            startHeistButton.setVisibility(Button.VISIBLE);
            endHeistButton.setVisibility(Button.INVISIBLE);
        }
    }

    public void openHeistActivity(String time) {
        Intent i = new Intent(MainActivity.this, SaveHeistActivity.class);
        i.putExtra(EXTRA_TIME, time);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startHeistButton = findViewById(R.id.startHeistButton);
        endHeistButton = findViewById(R.id.endHeistButton);
        chronometer = findViewById(R.id.heistChronometer);

        startHeistButton.setOnClickListener(view -> {
            chronometer.setVisibility(Chronometer.VISIBLE);
            chronometer.setBase(SystemClock.elapsedRealtime());

            chronometer.start();

            toggleEndButtonVisibility(true);
        });


        endHeistButton.setOnClickListener(view -> {
            chronometer.stop();
            chronometer.setVisibility(Chronometer.INVISIBLE);

            //heistDuration = SystemClock.elapsedRealtime() - chronometer.getBase() / 10 ;

            String heistDuration = (String) chronometer.getText();

            toggleEndButtonVisibility(false);
            openHeistActivity(heistDuration);
        });

    }


}