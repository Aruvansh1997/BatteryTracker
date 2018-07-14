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
    long original;
    MyCountDownTimer myCountDownTimer;
    ProgressBar progressBar;
    long timeleft;
    int progress;
    long TimeRecur;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_battery_counter);
        Bundle bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        time = bundle.getString("time");
       // original=Long.valueOf(bundle.getString("timeentered"));
        timeleft = Long.parseLong(time);

        name = bundle.getString("name");
        progress=100-Integer.parseInt(bundle.getString("progress"));
        Log.d("TAG", "onCreate:time left in the variable "+timeleft+progress);
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
                myCountDownTimer.intialProgress();
                myCountDownTimer.start();
            }
        });
        ImageButton imageButton1=findViewById(R.id.StopButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time=String.valueOf(TimeRecur/60000);
                Log.d("TAG", "onClicking stop button: "+progress);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(name, time+" "+progress+" "+original);
                Toast.makeText(getApplicationContext(), "Your time left"+progress, Toast.LENGTH_LONG).show();
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

           TimeRecur=millisUntilFinished;
            if(flag==-1){
                progressBar.setProgress(progress);
                flag=1;

            }
            else {
                progress+= (100 /(int)( / 60000));//change this
                Log.d("TAG", "onTick: " + progress);
                progressBar.setProgress(progressBar.getMax() - progress);
            }

        }

        @Override
        public void onFinish() {
            Log.d("TAG", "onFinish:this is called ");
            progressBar.setProgress(0);
        }
        public void onPause() {
            myCountDownTimer.cancel();

        }
        public void intialProgress()
        {
            flag=-1;
        }

    }

}

