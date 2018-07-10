package com.aruvansh.bluetoothbatteryviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Calculation extends AppCompatActivity {

    String id,pos,name= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id");
        pos=bundle.getString("pos");
        name=bundle.getString("name");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText("This is the id="+id+ "This is the position="+pos+"This is the name="+name);
        TextView textView1=(TextView)findViewById(R.id.textView2);
        textView.setText("enter the claimed hours of usage by ="+name);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
       String list[]={"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                Log.d("TAG", "onItemSelected: "+item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
