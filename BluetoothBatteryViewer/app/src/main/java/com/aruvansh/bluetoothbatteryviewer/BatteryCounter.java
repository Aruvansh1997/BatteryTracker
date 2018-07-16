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
    int progress,progresstimer;
    long TimeRecur;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    int flag=1;
    TextView BatteryLevel,TimeElapsed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_battery_counter);
        Bundle bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        time = bundle.getString("time");
        progresstimer=bundle.getInt("progresstimer");
        timeleft = Long.parseLong(time);
        BatteryLevel=(TextView)findViewById(R.id.BatteryLevel);
        TimeElapsed=(TextView)findViewById(R.id.TimeElapsed);
        name = bundle.getString("name");
        progress=Integer.parseInt(bundle.getString("progress"));
        TextView textView = (TextView) findViewById(R.id.DisplayText);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        textView.setText("The battery consumption by your device " + name);
        ImageButton imageButton = findViewById(R.id.StartButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(progress);
                myCountDownTimer = new MyCountDownTimer( 6000, 1000);
                //myCountDownTimer.intialProgress();
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
                editor.putString(name, time+" "+progress+" "+Integer.toString(progresstimer));
                Toast.makeText(getApplicationContext(), "Your time left"+progress+" "+progresstimer+" "+time, Toast.LENGTH_LONG).show();
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
            Log.d("TAG", "onTick:millis unti finished "+millisUntilFinished);
          //  if(flag==-1){
            //    progressBar.setProgress(progressBar.getMax()-progress);
             //   flag=1;

            //}
            //else {
                //progressBar.setProgress(progressBar.getMax() - progress);
                //progress+=progresstimer;//change this
                //Log.d("TAG", "onTick: " + progress);


            //}
            Toast.makeText(getApplicationContext(), "BatteryLevel "+progress ,Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onTick: "+progress);
            // BatteryLevel.setText(progress);
            progress+=progresstimer;
//            TimeElapsed.setText((int)millisUntilFinished/60000);

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

