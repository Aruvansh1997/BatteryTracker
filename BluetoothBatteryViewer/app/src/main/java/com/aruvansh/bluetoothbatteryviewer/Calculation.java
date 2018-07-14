package com.aruvansh.bluetoothbatteryviewer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Calculation extends AppCompatActivity {

    String id,pos,name= null;
    long time,progress,timeentered;
    Object item;

    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
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
        if (sharedPreferences.contains(name)) {
            String x=sharedPreferences.getString(name," ");
           String temp[]=x.split(" ");
            progress=Long.parseLong(temp[1]);
            time= Long.parseLong(temp[0]);
            Toast.makeText(getApplicationContext(), "Already Stored "+ time ,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, BatteryCounter.class);
             Bundle bundle1=new Bundle();
            bundle1.putString("name",name);
            bundle1.putString("progress",String.valueOf(progress));
             bundle1.putString("time", String.valueOf(time));
           //  bundle1.putString("timeentered",String.valueOf(timeentered));
            intent.putExtras(bundle1);
            startActivity(intent);

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
                    editor.putString(name, Long.toString(time)+" "+"0");
                    Log.d("TAG", "onCreate:We have reached here "+time);
                    editor.commit();
                    v.startAnimation(animRotate);
                    Toast.makeText(getApplicationContext(), "Your Preference has been Saved", Toast.LENGTH_LONG).show();
                    TextView textView = (TextView) findViewById(R.id.displaymessage);
                    textView.setText("Now you can go back and start with the calculation you can also reset the value of the claimed hours");

                }
            });

            TextView textView1 = (TextView) findViewById(R.id.textView2);
            textView1.setText("Please enter the claimed hours of battery time by your device " + name);
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            String list[] = {"3", "120", "180", "240", "300", "360", "420", "480", "540", "600"};
            ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(aa);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    item = parent.getItemAtPosition(pos);
                    String stringToConvert = String.valueOf(item);
                    Long convertedLong = Long.parseLong(stringToConvert);
                    time = convertedLong;
                    ((TextView) view).setTextColor(Color.RED);

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }

}

