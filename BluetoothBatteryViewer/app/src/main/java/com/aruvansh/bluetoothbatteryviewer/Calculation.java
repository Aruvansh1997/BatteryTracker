package com.aruvansh.bluetoothbatteryviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Calculation extends AppCompatActivity {

    String id,pos,name= null;
    long time;
    CountDownTimer mCountDownTimer;
    MyCountDownTimer myCountDownTimer;
    ProgressBar progressBar;
    Object item;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String TotalTime = "TotalTime";
    public static final String TimeLeft = "TimeLeft";
    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id");
        pos=bundle.getString("pos");
        name=bundle.getString("name");

        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        Button save=(Button)findViewById(R.id.save);
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Name)) {
            time= Long.parseLong(sharedPreferences.getString(TotalTime," "));
            Toast.makeText(getApplicationContext(), "Already Stored "+ time ,Toast.LENGTH_SHORT).show();
            flag=-1;
        }
        else {
            save.setOnClickListener(new Button.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Name, name);
                    editor.putString(TotalTime, Long.toString(time));
                    editor.commit();
                    v.startAnimation(animRotate);
                }
            });

        }
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        TextView textView1=(TextView)findViewById(R.id.textView2);
        textView1.setText("Please enter the claimed hours of battery time by your device "+ name);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String list[]={"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (flag != -1) {
                    item = parent.getItemAtPosition(pos);
                    String stringToConvert = String.valueOf(item);
                    Long convertedLong = Long.parseLong(stringToConvert);
                    time = convertedLong;
                    ((TextView) view).setTextColor(Color.RED);
                } else {
                    Log.d("TAG", "onItemSelected: " + item);
                    ImageButton imageButton = findViewById(R.id.imageButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setProgress(100);
                            myCountDownTimer = new MyCountDownTimer(time * 3600000, 60000);
                            myCountDownTimer.start();
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        


    }
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/100);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {

            progressBar.setProgress(0);
        }

    }

}

