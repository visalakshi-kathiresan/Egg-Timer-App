package com.example.eggtimerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView txt;
    Button btn;
    CountDownTimer countDownTimer;
    boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        btn = findViewById(R.id.btn);
        txt = findViewById(R.id.txt);

        seekBar.setMax(600); // 10 minutes

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    resetTimer();
                } else {
                    isTimerRunning = true;
                    btn.setText("STOP");
                    seekBar.setEnabled(false);

                    countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000L, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimer((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            txt.setText("0:00");
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alaram);
                            mediaPlayer.start();
                            resetTimer();
                        }
                    }.start();
                }
            }
        });
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String secInString = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        txt.setText(minutes + ":" + secInString);
    }

    public void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        updateTimer(30);
        btn.setText("START");
        isTimerRunning = false;
    }
}
