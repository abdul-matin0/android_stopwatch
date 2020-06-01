package com.matin.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView timerTextView;
    TextView lapTextView;
    Button startStopBtn;
    CountDownTimer countDownTimer;
    Boolean isActive = false;
    Intent intent;
    ImageView glow;
    String time;
    boolean glowEnabled = false;
    int result;

    //timer stops || reset
    public void stopTimer(View view){
        isActive = false;
        seekBar.setEnabled(true);
        seekBar.setProgress(0);
        countDownTimer.cancel();
        timerTextView.setText("00:00");
        startStopBtn.setText("START");
        glow.animate().scaleX(0.2f).scaleY(0.2f).setDuration(900).alpha(0.07f);
    }

    public void startTimer(View view){

        if(isActive){
            //lap
            time = updateTimer(result);
            lapTextView.setText(time);
        }else{
            //timer starts
            isActive = true;
            startStopBtn.setText("LAP");
            seekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000){

                @Override
                public void onTick(long millisUntilFinished) {

                    //glow effect
                    result = (int) millisUntilFinished / 1000;
                    if(glowEnabled){
                        //zoom out
                        glowEnabled = false;
                        glow.animate().scaleX(0.9f).scaleY(0.9f).setDuration(1000).alpha(0.09f);
                    }else{
                        //zoom in
                        glowEnabled = true;
                        glow.animate().scaleX(1.08f).scaleY(1.08f).setDuration(1000).alpha(0.09f);
                    }
                    //set time to timerTextView
                    time = updateTimer(result);
                    timerTextView.setText(time);
                }

                @Override
                public void onFinish() {
                    //stop timer and play sound
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.dingdong);
                    mediaPlayer.start();
                    stopTimer(seekBar);
                }
            }.start();
        }

    }

    public String updateTimer(int progress){

        int mins = progress / 60;
        int seconds = progress - (mins * 60);
        String minStr = Integer.toString(mins);
        String secStr = Integer.toString(seconds);

        if(mins <= 9){
            minStr = "0"+ minStr;
        }
        if(seconds <= 9){
            secStr = "0" + secStr;
        }
       return minStr + ":" + secStr;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        glow = (ImageView) findViewById(R.id.glow);
        startStopBtn = (Button) findViewById(R.id.startStopBtn);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        lapTextView = (TextView) findViewById(R.id.lapTextView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setMax(600);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //set time to timerTextView
                time = updateTimer(progress);
                timerTextView.setText(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void openAlarmActivity(View view){
        intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }
}
