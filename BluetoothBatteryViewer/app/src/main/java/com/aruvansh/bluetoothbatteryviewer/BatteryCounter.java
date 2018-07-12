package com.aruvansh.bluetoothbatteryviewer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryCounter extends AppCompatActivity {

    String name, time = null;
    MyCountDownTimer myCountDownTimer;
    ProgressBar progressBar;
    Long timeleft;
    int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_counter);
        Bundle bundle = getIntent().getExtras();
        time = bundle.getString("time");
        timeleft = Long.parseLong(time);
        name = bundle.getString("name");
        TextView textView = (TextView) findViewById(R.id.textView);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        textView.setText("The battery consumption by your device" + name);
        ImageButton imageButton = findViewById(R.id.StartButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(progress);
                myCountDownTimer = new MyCountDownTimer(timeleft * 3600000, 60000);
                myCountDownTimer.start();
            }
        });

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            progress = (int) (millisUntilFinished / 100);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {

            progressBar.setProgress(0);
        }

    }
}
