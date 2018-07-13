package com.aruvansh.bluetoothbatteryviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BatteryCounter extends AppCompatActivity {

    String name,time = null;
    MyCountDownTimer myCountDownTimer;
    ProgressBar progressBar;
    Long timeleft;
    int progress;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_battery_counter);
        Bundle bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        time = bundle.getString("time");
        timeleft = Long.parseLong(time);
        name = bundle.getString("name");
        progress=Integer.parseInt(bundle.getString("progress"));
        TextView textView = (TextView) findViewById(R.id.DisplayText);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        Log.d("geuif uiefio ewifgew f", "onCreate: "+progress);
        textView.setText("The battery consumption by your device " + name);
        ImageButton imageButton = findViewById(R.id.StartButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(progress);
                myCountDownTimer = new MyCountDownTimer(timeleft * 60000, 60000);
                Toast.makeText(getApplicationContext(), "Your current pogree"+progress, Toast.LENGTH_LONG).show();
                myCountDownTimer.intialProgress(progress);
                myCountDownTimer.start();
            }
        });
        ImageButton imageButton1=findViewById(R.id.StopButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time=String.valueOf(progress/600);
                Log.d("TAG", "onClicking stop button: "+progress);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(name, time+" "+progress);
                Toast.makeText(getApplicationContext(), "Your time left"+time, Toast.LENGTH_LONG).show();
                editor.commit();
                myCountDownTimer.onPause();

           }
        });

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            Log.d("hriofb ghreio gior", "onTick: "+millisUntilFinished);
            progress = (int) (millisUntilFinished / 60000);

            progressBar.setProgress(progressBar.getMax()-progress);

        }

        @Override
        public void onFinish() {

            progressBar.setProgress(0);
        }
        public void onPause() {
            myCountDownTimer.cancel();

        }
        public void intialProgress(int progress)
        {
            progressBar.setProgress(progress);
        }

    }

}

