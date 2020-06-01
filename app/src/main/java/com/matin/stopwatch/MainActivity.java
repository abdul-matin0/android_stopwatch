package com.matin.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView alarmClock = (ImageView) findViewById(R.id.alarmClock);
        TextView clockTextView = (TextView) findViewById(R.id.clockTextView);

        //animate
        clockTextView.animate().setDuration(1000).alpha(1);
        alarmClock.setY(3000);
        alarmClock.animate().translationYBy(-3000).rotation(1800).setDuration(2000);

        //create splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);

    }

}
