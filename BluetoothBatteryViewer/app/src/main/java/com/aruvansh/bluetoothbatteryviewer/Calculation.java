package com.aruvansh.bluetoothbatteryviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Calculation extends AppCompatActivity {

    String id,pos= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id");
        pos=bundle.getString("pos");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText("This is the is="+id+ "This is the position="+pos);
    }
}
