package com.example.misio.losowanko;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

    Button buttonDodajOsobe,buttonDodajZadanie, buttonLosuj, buttonWroc, buttonZapisz;
    TableLayout tableOsoby,tableZadania;
    EditText editTextOsoba,editTextZadanie;
    ArrayList<String> listaOsob;
    ArrayList<String> listaZadan;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losowanie);

        //Deklaracja komponentow
        buttonDodajOsobe = (Button)findViewById(R.id.buttonDodajOsobe);
        buttonDodajZadanie = (Button)findViewById(R.id.buttonDodajZadanie);
        buttonWroc = (Button)findViewById(R.id.buttonWroc);
        buttonLosuj = (Button)findViewById(R.id.buttonLosuj);
        buttonZapisz = (Button)findViewById(R.id.buttonZapisz);
        tableOsoby = (TableLayout)findViewById(R.id.tableOsoby);
        tableZadania = (TableLayout)findViewById(R.id.tableZadania);
        editTextOsoba = (EditText)findViewById(R.id.editTextOsoba);
        editTextZadanie = (EditText)findViewById(R.id.editTextZadanie);
        listaOsob = new ArrayList<String>();
        listaZadan = new ArrayList<String>();

        //Obsluga klikniec
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
        buttonWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAllert();

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

    public void buildAllert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Potwierdź wyjście");
        builder.setMessage("Wyjscie bez zapisu usunie wszystkie dane!");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
