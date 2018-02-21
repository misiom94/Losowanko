package com.example.misio.losowanko;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ActivityWynik extends AppCompatActivity {
    private static final String TAG = "ActivityWynik";
    TableLayout tabelaWyniki;
    TableRow rowWyniki;
    Button buttonPowrot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wynik);

        Intent intent = getIntent();
        HashMap<String,String> wynik = (HashMap<String,String>)intent.getSerializableExtra("map");
        buttonPowrot = (Button)findViewById(R.id.buttonWroc);
        tabelaWyniki = (TableLayout)findViewById(R.id.tableWynik);
        fillTable(wynik,tabelaWyniki);

    }
    public void fillTable(HashMap<String,String> wynik, TableLayout tabelaWyniki){
        for(String key : wynik.keySet()){
            String value = wynik.get(key);
            TableRow rowWynik = new TableRow(this);
            rowWynik.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
            TextView viewWynik = new TextView(this);
            viewWynik.setText("Osoba:"+key+"\n"+value+"\n\n");

            setViewParams(viewWynik);
            tabelaWyniki.addView(rowWynik);
            tabelaWyniki.addView(viewWynik);



        }
    }

    public void setViewParams(TextView textView){

        textView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.GRAY);
    }

}