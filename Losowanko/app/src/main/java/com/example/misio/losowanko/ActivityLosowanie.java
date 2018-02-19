package com.example.misio.losowanko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityLosowanie extends AppCompatActivity {

    Button buttonDodajOsobe;
    TableLayout tableOsoby;
    EditText editTextOsoba;
    ArrayList<String> listaOsob;
    ArrayList<String> listaZadan;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losowanie);


        buttonDodajOsobe = (Button)findViewById(R.id.buttonDodaj);
        tableOsoby = (TableLayout)findViewById(R.id.tableOsoby);
        editTextOsoba = (EditText)findViewById(R.id.editTextOsoba);
        listaOsob = new ArrayList<String>();
        listaZadan = new ArrayList<String>();
        buttonDodajOsobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkField(editTextOsoba)){
                    addPosition(editTextOsoba);
                    refreshTable();
                }
            }
        });
    }

    public boolean checkField(TextView textView){
        if(textView.getText().toString()==""){
            Toast.makeText(this, "Zadne pole nie moze byc puste!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void addPosition(EditText editText){
        Log.d("DODAJ OSOBE: ",String.valueOf(editText.getText()));
        String osoba = String.valueOf(editText.getText());
        listaOsob.add(osoba);
    }

    public void removePosition(int i){
        Log.d("USUWANIE","USUNIETO " + String.valueOf(listaOsob.get(i)));
        listaOsob.remove(i);
        refreshTable();
    }

    public void refreshTable(){
        tableOsoby.removeAllViews();
        for (int i=0; i<listaOsob.size();i++){
            TableRow rowOsoba = new TableRow(this);
            Button buttonDelete = new Button(this);
            buttonDelete.setText("Usun");
            rowOsoba.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView textView = new TextView(this);
            textView.setText(listaOsob.get(i));
            rowOsoba.addView(textView);
            rowOsoba.addView(buttonDelete);
            tableOsoby.addView(rowOsoba);
            final int finalI = i;
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removePosition(finalI);
                }
            });
        }
    }
}
