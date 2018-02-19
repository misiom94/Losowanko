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

    Button buttonDodajOsobe,buttonDodajZadanie;
    TableLayout tableOsoby,tableZadania;
    EditText editTextOsoba,editTextZadanie;
    ArrayList<String> listaOsob;
    ArrayList<String> listaZadan;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losowanie);


        buttonDodajOsobe = (Button)findViewById(R.id.buttonDodajOsobe);
        buttonDodajZadanie = (Button)findViewById(R.id.buttonDodajZadanie);
        tableOsoby = (TableLayout)findViewById(R.id.tableOsoby);
        tableZadania = (TableLayout)findViewById(R.id.tableZadania);
        editTextOsoba = (EditText)findViewById(R.id.editTextOsoba);
        editTextZadanie = (EditText)findViewById(R.id.editTextZadanie);
        listaOsob = new ArrayList<String>();
        listaZadan = new ArrayList<String>();
        buttonDodajOsobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkField(editTextOsoba)){
                    addPosition(editTextOsoba,listaOsob);
                    refreshTable(tableOsoby,listaOsob);
                }
            }
        });
        buttonDodajZadanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkField(editTextZadanie)){
                    addPosition(editTextZadanie,listaZadan);
                    refreshTable(tableZadania,listaZadan);
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

    public void addPosition(EditText editText,ArrayList<String> arrayList){
        Log.d("DODAWANIE POZYCJI: ","DODANO: " + String.valueOf(editText.getText()));
        String position = String.valueOf(editText.getText());
        arrayList.add(position);
    }

    public void removePosition(int i,ArrayList<String> arrayList,TableLayout tableLayout){
        Log.d("USUWANIE POZYCJI","USUNIETO " + String.valueOf(arrayList.get(i)));
        arrayList.remove(i);
        refreshTable(tableLayout,arrayList);
    }

    public void refreshTable(final TableLayout tableLayout, final ArrayList<String> arrayList){
        tableLayout.removeAllViews();
        for (int i=0; i<arrayList.size();i++){
            TableRow rowOsoba = new TableRow(this);
            Button buttonDelete = new Button(this);
            buttonDelete.setText("Usun");
            rowOsoba.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView textView = new TextView(this);
            textView.setText(arrayList.get(i));
            rowOsoba.addView(textView);
            rowOsoba.addView(buttonDelete);
            tableLayout.addView(rowOsoba);
            final int finalI = i;
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removePosition(finalI,arrayList,tableLayout);
                }
            });
        }
    }
}
