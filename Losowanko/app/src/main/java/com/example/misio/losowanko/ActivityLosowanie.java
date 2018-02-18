package com.example.misio.losowanko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class ActivityLosowanie extends AppCompatActivity {

    Button buttonDodaj = (Button) findViewById(R.id.buttonDodaj);
//    TableLayout tableOsoby = (TableLayout) findViewById(R.id.tableOsoby);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losowanie);
    }

    public boolean checkField(TextView textView, Button button){

    }
}
