package com.example.misio.losowanko;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Misio on 2018-02-17.
 */

public class Database extends SQLiteOpenHelper{

    //LOG TAG
    private final static String TAG = "DATABASE";

    //DATABASE
    private final static String DB_NAME = "losowanie.db";
    private final static int DB_VERSION = 1;

    //TABLES NAME
    private final static String TABLE_LOSOWANIA = "losowania";
    private final static String TABLE_OSOBY = "osoby";
    private final static String TABLE_ZADANIA = "zadania";

    //TABLE OSOBY
    private final static String OSOBA_ID = "id_osoba";
    private final static String OSOBA_NAZWA = "osoba_nazwa";

    //TABLE ZADANIA
    private final static String ZADANIE_ID = "id_zadanie";
    private final static String ZADANIE_NAZWA = "zadanie_nazwa";

    //TABLE LOSOWANIA
    private final static String LOSOWANIE_ID = "id_losowanie";
    private final static String OSOBA_ID_FK = "id_osoba_fk";
    private final static String ZADANIE_ID_FK = "id_zadanie_fk";

    //CREATING TABLES
    private final static String tableOsoby = "CREATE TABLE " + TABLE_OSOBY +
            "(" + OSOBA_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            OSOBA_NAZWA + "TEXT NOT NULL);";
    private final static String tableZadania = "CREATE TABLE " + TABLE_ZADANIA +
            "(" + ZADANIE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            ZADANIE_NAZWA + "TEXT NOT NULL);";
    private final static String tableLosowania = "CREATE TABLE " + TABLE_LOSOWANIA +
            "(" + LOSOWANIE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            OSOBA_ID_FK + "INTEGER FOREIGN KEY REFERENCES " + TABLE_OSOBY + "(" + OSOBA_ID + ")" +
            ZADANIE_ID_FK + "INTEGER FOREIGN KEY REFERENCES " + TABLE_ZADANIA + "(" + ZADANIE_ID + ");";
//    private final static String tableLosowania = "CREATE TABLE " + TABLE_LOSOWANIA +
//            "(" + LOSOWANIE_ID + "INTEGER PRIMARY KEY," +
//            OSOBA_NAZWA + "TEXT " +
//            ZADANIE_NAZWA + "TEXT );";


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tableOsoby);
        sqLiteDatabase.execSQL(tableZadania);
        sqLiteDatabase.execSQL(tableLosowania);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + TABLE_OSOBY);
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + TABLE_ZADANIA);
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + TABLE_LOSOWANIA);
    }

    public boolean addOsoba(Osoba osoba){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OSOBA_NAZWA, osoba.getNazwa());
        Log.i(TAG,"Dodano osobe: " + osoba.getNazwa());
        long result = db.insert(TABLE_OSOBY,null,values);
        return result != -1;
    }

    public boolean addZadanie(Zadanie zadanie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ZADANIE_NAZWA, zadanie.getNazwa());
        Log.i(TAG,"Dodano zadanie: " + zadanie.getNazwa());
        long result = db.insert(TABLE_ZADANIA,null,values);
        return result != -1;
    }

    public int getLastId(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX("+LOSOWANIE_ID+") FROM "+TABLE_LOSOWANIA;
        Cursor data = db.rawQuery(query,null);
        int id = data.getInt(data.getColumnIndex(LOSOWANIE_ID));
        Log.i(TAG,"LAST ID: "+data);
        return id;
    }

    public Cursor pobierzOsoby() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_OSOBY;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}
