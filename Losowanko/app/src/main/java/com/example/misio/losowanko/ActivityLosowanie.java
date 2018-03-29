package com.example.misio.losowanko;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ActivityLosowanie extends AppCompatActivity {

    private static final String TAG = "ActivityLosowanie";
    Button buttonDodajOsobe,buttonDodajZadanie, buttonLosuj, buttonWroc, buttonZapisz;
    Database db;
    TableLayout tableOsoby,tableZadania;
    EditText editTextOsoba,editTextZadanie;
    ArrayList<String> listaOsob;
    ArrayList<String> listaZadan;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
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
        initializeShakeDetection();
//        validateStartPermission();
        mock();

        //Obsluga klikniec
        buttonDodajOsobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkField(editTextOsoba)){
                    addPosition(editTextOsoba,listaOsob);
                    refreshTable(tableOsoby,listaOsob);
                    editTextOsoba.setText("");
                    validateStartPermission();
                }
            }
        });
        buttonDodajZadanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkField(editTextZadanie)){
                    addPosition(editTextZadanie,listaZadan);
                    refreshTable(tableZadania,listaZadan);
                    editTextZadanie.setText("");
                    validateStartPermission();
                }
            }
        });
        buttonWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listaOsob.size()>0 || listaZadan.size()>0){
                    buildAllert();
                }else{
                    finish();
                }
            }
        });
        buttonLosuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateStartPermission()){
                    if(listaZadan.size()>=listaOsob.size()){
                        HashMap wynik = losujZadania(listaZadan,listaOsob);
                        if(wynik!=null){
                            sendResultIntent(wynik);
                        }
                    }else{
                        HashMap wynik = losujZadania(listaOsob,listaZadan);
                        if(wynik!=null){
                            sendResultIntent(wynik);
                        }
                    }
                }
            }
        });
    }

    public void mock(){
        listaOsob.add("user1");
        listaOsob.add("user2");
        listaOsob.add("user3");
        listaOsob.add("user4");
        listaOsob.add("user5");
        listaOsob.add("user6");
        listaZadan.add("ex1");
        listaZadan.add("ex2");
        listaZadan.add("ex3");
        listaZadan.add("ex1");
        listaZadan.add("ex2");
        listaZadan.add("ex3");
    }

    public void initializeShakeDetection(){
        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if(validateStartPermission()){
                    HashMap wynik = losujZadania(listaZadan,listaOsob);
                    sendResultIntent(wynik);
                }
            }
        });
    }

    public void saveDataToDb(ArrayList listaOsob, ArrayList listaZadan) {

        db = new Database(this);
        int id = db.getLastId();
        Log.i(TAG, String.valueOf(id));
        for (int i = 0; i < listaOsob.size(); i++) {
            db.addOsoba(new Osoba((String) listaOsob.get(i)));
        }
        for(int i = 0;i < listaZadan.size();i++){
            db.addZadanie(new Zadanie((String) listaZadan.get(i)));
        }

    }

    public boolean checkField(TextView textView){
        if(textView.getText().toString().equals("")){
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
            buttonDelete.setBackgroundResource(R.drawable.remove);
            //buttonDelete.setText("Usun");
            buttonDelete.setLayoutParams(new TableRow.LayoutParams(120,120));
            rowOsoba.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView textView = new TextView(this);
            textView.setText(arrayList.get(i));
            rowOsoba.addView(textView);
            rowOsoba.addView(buttonDelete);
            tableLayout.addView(rowOsoba);
            final int finalI = i;
            validateStartPermission();
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removePosition(finalI,arrayList,tableLayout);
                }
            });
        }
    }
    public boolean validateStartPermission(){
        if(listaZadan.size()==0 || listaOsob.size()==0){
            buttonLosuj.setEnabled(false);
            Toast.makeText(this,"Dane na jednej z list nie są uzupełnione!",Toast.LENGTH_SHORT).show();
            return false;
        }
        buttonLosuj.setEnabled(true);
        return true;
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

    public void sendResultIntent(HashMap hashMap){
        Intent intent = new Intent(this, ActivityWynik.class);
        intent.putExtra("map", (Serializable)hashMap);
        startActivity(intent);
    }

    public HashMap losujZadania(ArrayList<String> listaZadan, ArrayList<String> listaOsob){
        HashMap<String,String> wynik = new HashMap<>();
        int iloscZadan = listaZadan.size();
        int iloscOsob = listaOsob.size();
        if(iloscZadan%iloscOsob==0){
            int przydzial = iloscZadan/iloscOsob;
            wynik = przydzielZadania(listaZadan,listaOsob,przydzial);
            return wynik;
        }else{
            Toast.makeText(this,"Ilosc zadan musi byc podzielna przez ilosc osob!",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public HashMap<String,String> przydzielZadania(ArrayList<String> listaZadan, ArrayList<String> listaOsob, int przydzial){
        HashMap<String,String> result = new HashMap<>();
        ArrayList<String> editableListaZadan;
        editableListaZadan = copyListy(listaZadan);
        List<String> przypisaneZadania;
        Collections.shuffle(editableListaZadan);
        for (String osoba:listaOsob) {
            przypisaneZadania = editableListaZadan.subList(0,przydzial);
            Log.i(TAG,String.valueOf(przypisaneZadania.size())+ " DLUGOSC LISTY");
            String zadania = "";
            for(int i=0;i<przypisaneZadania.size();i++){
                zadania+=String.valueOf(przypisaneZadania.get(i))+" ";
            }
            result.put(osoba,zadania);
            Log.i(TAG,result.toString());
            editableListaZadan.subList(0,przydzial).clear();
        }
        return result;
    }

    public ArrayList<String> copyListy(ArrayList<String> listaZadan){
        ArrayList<String> listToEdit = new ArrayList<>();
        listToEdit.addAll(listaZadan);
        return listToEdit;
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }
}

